package jlox;

import java.util.ArrayList;
import java.util.List;

class LoxListClass implements LoxCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Object arg = arguments.get(0);
        if (!(arg instanceof ArrayList)) {
            throw new RuntimeError(
                    new Token(TokenType.IDENTIFIER, "List", null, 0),
                    "List initializer must be an array literal.");
        }

        @SuppressWarnings("unchecked")
        ArrayList<Object> initial = (ArrayList<Object>) arg;
        return new LoxList(new ArrayList<>(initial));
    }

    @Override
    public String toString() {
        return "<class List>";
    }
}
