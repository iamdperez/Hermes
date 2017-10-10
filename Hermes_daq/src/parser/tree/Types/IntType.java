package parser.tree.Types;

import parser.tree.Values.IntValue;
import parser.tree.Values.Value;

public class IntType extends Type {
    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }
}
