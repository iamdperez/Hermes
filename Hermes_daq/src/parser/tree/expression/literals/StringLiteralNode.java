package parser.tree.expression.literals;

import parser.tree.Location;
import parser.tree.Types.StringType;
import parser.tree.Types.Type;
import parser.tree.Values.StringValue;
import parser.tree.Values.Value;

public class StringLiteralNode extends LiteralNode {
    public StringLiteralNode(Location location, String value) {
        super(location, value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value Interpret() {
        return new StringValue("");
    }

    @Override
    public Type EvaluateSemantic() {
        return new StringType();
    }
}
