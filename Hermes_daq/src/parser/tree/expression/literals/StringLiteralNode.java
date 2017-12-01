package parser.tree.expression.literals;

import parser.ParserUtils;
import parser.tree.Location;
import parser.tree.types.StringType;
import parser.tree.types.Type;
import parser.tree.values.SetValue;
import parser.tree.values.StringValue;
import parser.tree.values.Value;

public class StringLiteralNode extends LiteralNode {
    public StringLiteralNode(Location location, String value) {
        super(location, value);
    }

    @Override
    public Type getType() {
        return ParserUtils.stringType;
    }

    @Override
    public Value interpret() {
        return new StringValue((String)getValue());
    }

    @Override
    public Type evaluateSemantic() {
        return ParserUtils.stringType;
    }
}
