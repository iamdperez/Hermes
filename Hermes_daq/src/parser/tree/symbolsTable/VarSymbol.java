package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

public class VarSymbol implements Symbol {
    private IntValue value;
    @Override
    public Type getType() {
        return ParserUtils.intType;
    }

    @Override
    public Value getDefaultValue() {
        value = new IntValue();
        return value;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = (IntValue)value;
    }
}
