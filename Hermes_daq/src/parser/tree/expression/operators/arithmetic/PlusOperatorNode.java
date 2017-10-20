package parser.tree.expression.operators.arithmetic;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.Types.IntType;
import parser.tree.Types.StringType;
import parser.tree.Types.Type;
import parser.tree.Values.IntValue;
import parser.tree.Values.StringValue;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class PlusOperatorNode extends BinaryOperator {
    private String _returnValue;
    public PlusOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() {
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
                ||(isStringType(right)&&isIntType(left))){
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
