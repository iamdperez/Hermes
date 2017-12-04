package parser.tree.interfaces;

import parser.exeptions.SemanticException;
import parser.tree.types.Type;
import parser.tree.values.Value;
import serialCommunication.SerialCommException;

public interface Symbol {
    Type getType();
    Value getDefaultValue();
    Value getValue() throws SemanticException, SerialCommException;
    void setValue(Value value);
}
