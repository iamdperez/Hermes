package parser.tree.expression.operators.bits;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;
import serialCommunication.SerialCommException;

public class AndNode extends BinaryOperator {
    public AndNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException, SerialCommException {
        Value right = getRightNode().interpret();
        Value left = getLeftNode().interpret();
        int result = (int)left.getValue() & (int)right.getValue();
        return new IntValue(result);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return validateBitwiseSemanticOperator("And");
    }
}
