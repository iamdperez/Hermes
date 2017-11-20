package parser.tree.expression.literals;

import parser.ParserUtils;
import parser.tree.Location;
import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

public class IntLiteralNode extends LiteralNode {
    public IntLiteralNode(Location location, Integer value) {

        super(location, value);
    }

    @Override
    public Type getType() {
        return ParserUtils.intType;
    }

    @Override
    public Value interpret() {
        return new IntValue((Integer) getValue());
    }

    @Override
    public Type evaluateSemantic() {
        return ParserUtils.intType;
    }
}
