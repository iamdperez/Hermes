package parser.tree.expression.operators.logical;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.Types.IntType;
import parser.tree.Types.SetType;
import parser.tree.Types.Type;
import parser.tree.Values.IntValue;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class AndOperatorNode extends BinaryOperator {
    private String nodeType;
    public AndOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        if(nodeType.equals("int")){
            int iLeft = (Integer)left.getValue();
            int iRight = (Integer)right.getValue();
            int value = iLeft > 0 && iRight > 0 ? 1 : 0;
            return new IntValue(value);
        }
        return null;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if(isSetType(left)&&isSetType(right)){

            nodeType = "set";
            return new SetType();
        }else if(isIntType(left)&&isIntType(right)){
            nodeType = "int";
            return new IntType();
        }

        String errorMessage = getSemanticErrorMessage("And operator", left, right);
        throw new SemanticException(errorMessage);
    }
}
