package parser.tree.Types;

import parser.tree.Values.SetValue;
import parser.tree.Values.Value;

public class SetType extends Type {
    @Override
    public Value getDefaultValue() {
        return new SetValue();
    }
}
