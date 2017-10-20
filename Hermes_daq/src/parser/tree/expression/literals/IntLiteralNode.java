package parser.tree.expression.literals;

import parser.tree.Location;
import parser.tree.Types.IntType;
import parser.tree.Types.Type;
import parser.tree.Values.IntValue;
import parser.tree.Values.Value;

public class IntLiteralNode extends LiteralNode {
    public IntLiteralNode(Location location, Integer value) {

        super(location, value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value interpret() {
        return new IntValue((Integer) getValue());
    }

    @Override
    public Type evaluateSemantic() {
        return new IntType();
    }
}
