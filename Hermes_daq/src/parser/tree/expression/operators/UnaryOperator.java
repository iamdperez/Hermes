package parser.tree.expression.operators;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.IdNode;
import parser.tree.types.IntType;
import parser.tree.types.Type;
public abstract class UnaryOperator extends ExpressionNode {
    private ExpressionNode expression;

    public UnaryOperator(Location location){
        super(location);
    }
    public UnaryOperator(Location location, ExpressionNode expression) {
        super(location);
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    protected boolean nodeIsValid(Type t){
        return t instanceof IntType;
    }

    protected Type validatePosOrPreOperation(String operator) throws SemanticException {
        Type expType = getExpression().evaluateSemantic();
        validateIdNode(getExpression());
        if(!(getExpression() instanceof IdNode)) {
            throw new SemanticException(ParserUtils.getInstance()
                    .getLineErrorMessage("Expression must be a Id", getLocation()));
        }
        if(nodeIsValid(expType))
            return expType;
        throw  new SemanticException(ParserUtils.getInstance()
                .getLineErrorMessage(operator+" operator is not supported `"+expType
                        +"` types, must be a IntType.", getLocation()));
    }

    public void setExpression(ExpressionNode expression){
        this.expression = expression;
    }
}
