package jlox;

/**
 * @author: Robert Nystrom
 * @see: https://craftinginterpreters.com/
 */

import java.util.Map;

class LoxClass implements LoxCallable {
    final String name;
    private final Map<String, LoxFunction> methods;
    private final Map<String, LoxFunction> classMethods;

    LoxClass(String name, Map<String, LoxFunction> methods,
            Map<String, LoxFunction> classMethods) {
        this.name = name;
        this.methods = methods;
        this.classMethods = classMethods;
    }

    LoxFunction findMethod(String name) {
        return methods.get(name);
    }

    @Override
    public int arity() {
        LoxFunction initializer = findMethod("init");
        if (initializer == null)
            return 0;
        return initializer.arity();
    }

    @Override
    public Object call(Interpreter interpreter, java.util.List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    Object get(Token name) {
        LoxFunction classMethod = classMethods.get(name.lexeme);
        if (classMethod != null) {
            return classMethod;
        }
        throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
    }

    @Override
    public String toString() {
        return name;
    }
}
