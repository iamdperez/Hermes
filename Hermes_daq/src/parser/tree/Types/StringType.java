package parser.tree.types;

import parser.tree.values.StringValue;
import parser.tree.values.Value;

public class StringType extends Type {
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }
}
