package parser.tree.expression.literals;

import parser.tree.Location;
import parser.tree.types.StringType;
import parser.tree.types.Type;
import parser.tree.values.StringValue;
import parser.tree.values.Value;

public class StringLiteralNode extends LiteralNode {
    public StringLiteralNode(Location location, String value) {
        super(location, value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value interpret() {
        return new StringValue("");
    }

    @Override
    public Type evaluateSemantic() {
        return new StringType();
    }
}
