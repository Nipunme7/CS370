package jlox;

/**
 * @author: Robert Nystrom
 * @see: https://craftinginterpreters.com/
 */

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final boolean isInitializer;
    private final boolean isGetter;

    LoxFunction(Stmt.Function declaration, Environment closure,
            boolean isInitializer, boolean isGetter) {
        this.closure = closure;
        this.declaration = declaration;
        this.isInitializer = isInitializer;
        this.isGetter = isGetter;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    LoxFunction bind(LoxInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new LoxFunction(declaration, environment, isInitializer, isGetter);
    }

    boolean isGetter() {
        return isGetter;
    }

    @Override
    public Object call(Interpreter interpreter,
            List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme,
                    arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returnValue) {
            if (isInitializer) {
                return closure.get(new Token(TokenType.THIS, "this", null, 0));
            }
            return returnValue.value;
        }
        if (isInitializer) {
            return closure.get(new Token(TokenType.THIS, "this", null, 0));
        }
        return null;
    }
}