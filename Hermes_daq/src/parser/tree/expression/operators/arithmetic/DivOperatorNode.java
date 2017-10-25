package parser.tree.expression.operators.arithmetic;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class DivOperatorNode extends BinaryOperator {
    public DivOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        if((Integer)right.getValue() == 0)
            throw new ArithmeticException("Division by zero, line:"+
                    getLocation().getLine()+" column: "+getLocation().getColumn());
        return new IntValue(((Integer)left.getValue()) / ((Integer)right.getValue()));
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return evaluateIntSemantic("Division");
    }
}
