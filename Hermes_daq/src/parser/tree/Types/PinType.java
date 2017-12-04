package parser.tree.types;

import parser.tree.values.PinValue;
import parser.tree.values.Value;

public class PinType extends Type {
    @Override
    public Value getDefaultValue() {
        return new PinValue(0, "");
    }
    @Override
    public String toString() {
        return "PinType";
    }
}
