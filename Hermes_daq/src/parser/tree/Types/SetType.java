package parser.tree.types;

import parser.tree.values.SetValue;
import parser.tree.values.Value;

public class SetType extends Type {
    @Override
    public Value getDefaultValue() {
        return new SetValue(0, "");
    }
    @Override
    public String toString() {
        return "SetType";
    }
}
