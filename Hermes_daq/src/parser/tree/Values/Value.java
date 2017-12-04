package parser.tree.values;

import parser.exeptions.SemanticException;
import serialCommunication.SerialCommException;

public abstract class Value {
    public Value(Object value) {
    }

    public abstract Value clone();
    public abstract Object getValue() throws SemanticException, SerialCommException;
    public abstract void setValue(Object value) throws SemanticException, SerialCommException;
}
