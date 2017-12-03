package parser.tree.expression.operators.arithmetic;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.IntType;
import parser.tree.types.StringType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.StringValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;
import serialCommunication.SerialCommException;

public class PlusOperatorNode extends BinaryOperator {
    private String _returnValue;
    public PlusOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException, SerialCommException {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        switch (_returnValue){
            case "int":
                return new IntValue(((Integer)left.getValue()) + ((Integer)right.getValue()));
            case "string":
                return new StringValue((String)left.getValue() + right.getValue());
        }
        return null;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if((isStringType(left)&&isStringType(right))
                ||(isStringType(left)&&isIntType(right))
                ||(isStringType(right)&&isIntType(left)
                ||(isStringType(left)&&isSetType(right))
                ||(isSetType(left)&&isStringType(right))
                ||(isStringType(left)&&isPinType(right))
                ||(isPinType(left)&&isStringType(right)))){
            _returnValue = "string";
            return new StringType();
        }
        else if(isIntType(left)&&isIntType(right)){
            _returnValue = "int";
            return new IntType();
        }
        String errorMessage = getSemanticErrorMessage("Addition", left, right);
        throw new SemanticException(errorMessage);
    }


}
