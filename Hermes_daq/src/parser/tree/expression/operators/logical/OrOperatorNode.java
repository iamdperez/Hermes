package parser.tree.expression.operators.logical;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class OrOperatorNode extends BinaryOperator {
    public OrOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        int iLeft = (int)left.getValue();
        int iRight = (int)right.getValue();
        int value = iLeft > 0 || iRight > 0 ? 1 : 0;
        return new IntValue(value);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return validateLogicalSemanticOperation("Or");
    }
}
