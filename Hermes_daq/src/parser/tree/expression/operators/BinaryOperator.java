package parser.tree.expression.operators;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.*;
import parser.tree.expression.ExpressionNode;

public abstract class BinaryOperator extends ExpressionNode {
    private ExpressionNode leftNode;
    private ExpressionNode rightNode;

    public BinaryOperator(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public ExpressionNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(ExpressionNode leftNode) {
        this.leftNode = leftNode;
    }

    public ExpressionNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(ExpressionNode rightNode) {
        this.rightNode = rightNode;
    }

    protected boolean isStringType(Type t){
        return t instanceof StringType;
    }

    protected boolean isIntType(Type t){
        return t instanceof IntType;
    }

    protected boolean isSetType(Type t){
        return t instanceof SetType;
    }

    protected boolean isPinType(Type t){
        return  t instanceof PinType;
    }

    protected String getSemanticErrorMessage(String operation, Type left, Type right){
        return operation + " is not supported between "+
                left + " and " + right +" types. Line: "+
                getLocation().getLine() + " column: "+getLocation().getColumn();
    }

    protected Type evaluateIntSemantic(String operation) throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if(isIntType(left)&&isIntType(right)){
            return new IntType();
        }

        String errorMessage = getSemanticErrorMessage(operation, left, right);
        throw new SemanticException(errorMessage);
    }

    protected boolean typeIsValidForLogicalOperation(Type type){
        return isSetType(type) || isIntType(type) || isPinType(type);
    }

    protected Type validateLogicalSemanticOperation(String operator) throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());

        if(typeIsValidForLogicalOperation(left)&& typeIsValidForLogicalOperation(right))
            return ParserUtils.intType;

        String errorMessage = getSemanticErrorMessage(operator+ " operator", left, right);
        throw new SemanticException(errorMessage);
    }

    protected Type validateBitwiseSemanticOperator(String operator) throws SemanticException {
        Type left = getLeftNode().evaluateSemantic();
        Type right = getRightNode().evaluateSemantic();

        validateIdNode(getRightNode());
        validateIdNode(getLeftNode());
        if(isIntType(left) && isIntType(right))
            return left;
        String errorMessage = getSemanticErrorMessage(operator+ " bitwise operator", left, right);
        throw new SemanticException(errorMessage);
    }
}
