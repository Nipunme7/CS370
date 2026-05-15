package jlox;

import java.util.ArrayList;
import java.util.List;

class LoxList {
    final ArrayList<Object> elements;

    LoxList(ArrayList<Object> elements) {
        this.elements = elements;
    }

    Object get(Object indexObj, Token bracket) {
        return elements.get(resolveIndex(indexObj, bracket));
    }

    void set(Object indexObj, Object value, Token bracket) {
        elements.set(resolveIndex(indexObj, bracket), value);
    }

    Object getProperty(Token name) {
        switch (name.lexeme) {
            case "size":
                return new LoxCallable() {
                    @Override
                    public int arity() {
                        return 0;
                    }

                    @Override
                    public Object call(Interpreter interpreter, List<Object> arguments) {
                        return (double) elements.size();
                    }

                    @Override
                    public String toString() {
                        return "<fn size>";
                    }
                };
            case "append":
                return new LoxCallable() {
                    @Override
                    public int arity() {
                        return 1;
                    }

                    @Override
                    public Object call(Interpreter interpreter, List<Object> arguments) {
                        elements.add(arguments.get(0));
                        return null;
                    }

                    @Override
                    public String toString() {
                        return "<fn append>";
                    }
                };
            case "pop":
                return new LoxCallable() {
                    @Override
                    public int arity() {
                        return 0;
                    }

                    @Override
                    public Object call(Interpreter interpreter, List<Object> arguments) {
                        if (elements.isEmpty()) {
                            throw new RuntimeError(name, "Cannot pop from empty list.");
                        }
                        return elements.remove(elements.size() - 1);
                    }

                    @Override
                    public String toString() {
                        return "<fn pop>";
                    }
                };
            default:
                throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
        }
    }

    private int resolveIndex(Object indexObj, Token bracket) {
        if (!(indexObj instanceof Double)) {
            throw new RuntimeError(bracket, "List index must be a number.");
        }

        int index = (int) (double) indexObj;
        if (index < 0) {
            index += elements.size();
        }

        if (index < 0 || index >= elements.size()) {
            throw new RuntimeError(bracket, "List index out of bounds.");
        }

        return index;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(stringifyElement(elements.get(i)));
        }
        builder.append("]");
        return builder.toString();
    }

    private String stringifyElement(Object object) {
        if (object == null) {
            return "nil";
        }

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }
}
