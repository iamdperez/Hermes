package parser.tree.expression.operators.arithmetic;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class MinusOperatorNode extends BinaryOperator {
    public MinusOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        return new IntValue(((Integer)left.getValue()) - ((Integer)right.getValue()));
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return evaluateIntSemantic("Subtraction");
    }
}
