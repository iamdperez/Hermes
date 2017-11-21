package parser.tree.expression.operators.logical;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.IntType;
import parser.tree.types.SetType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.BinaryOperator;

public class AndOperatorNode extends BinaryOperator {
    public AndOperatorNode(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location, leftNode, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException {
        Value left = getLeftNode().interpret();
        Value right = getRightNode().interpret();
        int iLeft = (int)left.getValue();
        int iRight = (int)right.getValue();
        int value = iLeft > 0 && iRight > 0 ? 1 : 0;
        return new IntValue(value);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if(typeIsValid(left)&&typeIsValid(right))
            return new IntType();

        String errorMessage = getSemanticErrorMessage("And operator", left, right);
        throw new SemanticException(errorMessage);
    }

    private boolean typeIsValid(Type type){
        return isSetType(type) || isIntType(type) || isPinType(type);
    }
}
