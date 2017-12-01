package parser.tree.values;

import parser.exeptions.SemanticException;

public abstract class Value {
    public Value(Object value) {
    }

    public abstract Value clone();
    public abstract Object getValue() throws SemanticException;
    public abstract void setValue(Object value) throws SemanticException;
}
