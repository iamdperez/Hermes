package parser.tree.expression.operators.unary;

import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class PosIncrementOperatorNode extends UnaryOperator {
    public PosIncrementOperatorNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    @Override
    public Value interpret() {
        return null;
    }

    @Override
    public Type evaluateSemantic() {
        return null;
    }
}
