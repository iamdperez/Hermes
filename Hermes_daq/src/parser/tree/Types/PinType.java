package parser.tree.Types;

import parser.tree.Values.PinValue;
import parser.tree.Values.Value;

public class PinType extends Type {
    @Override
    public Value getDefaultValue() {
        return new PinValue(0);
    }
}
