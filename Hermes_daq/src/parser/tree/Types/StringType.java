package parser.tree.Types;

import parser.tree.Values.StringValue;
import parser.tree.Values.Value;

public class StringType extends Type {
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }
}
