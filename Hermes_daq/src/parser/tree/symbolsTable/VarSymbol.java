package parser.tree.symbolsTable;

import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

public class VarSymbol implements Symbol {
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue();
    }
}
