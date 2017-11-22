package parser.tree.interfaces;

import parser.exeptions.SemanticException;
import parser.tree.types.Type;
import parser.tree.values.Value;

public interface Symbol {
    Type getType();
    Value getDefaultValue();
    Value getValue() throws SemanticException;
    void setValue(Value value);
}
