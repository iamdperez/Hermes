package parser.tree.expression.operators.logical;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.IntValue;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class AndOperatorNode extends BinaryOperator {
    public AndOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        int iLeft = (Integer)left.getValue();
        int iRight = (Integer)right.getValue();
        int value = iLeft > 0 && iRight > 0 ? 1 : 0;
        return new IntValue(value);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return evaluateIntSemantic("And operator");
    }
}
