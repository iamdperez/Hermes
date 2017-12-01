package parser.tree.types;

import parser.tree.values.IntValue;
import parser.tree.values.Value;

public class IntType extends Type {
    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "IntType";
    }
}
