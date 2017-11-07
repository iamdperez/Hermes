package parser.tree.symbolsTable;

import parser.tree.types.Type;
import parser.tree.values.Value;

public interface Symbol {
    Type getType();
    Value getDefaultValue();
}
