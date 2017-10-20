package parser.tree.expression.operators.bits;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class LeftShiftOperatorNode extends BinaryOperator {
    public LeftShiftOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
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
