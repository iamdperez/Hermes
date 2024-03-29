package parser.tree.expression.operators.relational;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.interfaces.RelationalOperator;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;
import serialCommunication.SerialCommException;

public class GreaterEqualOperatorNode extends BinaryOperator implements RelationalOperator {
    public GreaterEqualOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException, SerialCommException {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        int iLeft = (int)left.getValue();
        int iRight = (int)right.getValue();
        int value = iLeft >= iRight  ? 1 : 0;
        return new IntValue(value);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if(operationIsValid(left,right))
            return ParserUtils.intType;
        String errorMessage = getSemanticErrorMessage("Greater Equal operator", left, right);
        throw new SemanticException(errorMessage);
    }

    private boolean operationIsValid(Type left, Type right){
        return (isIntType(left)&&isIntType(right));
    }
}
