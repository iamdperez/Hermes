package parser.tree.statements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

public class ExpressionStatementNode extends StatementNode {
    private final ExpressionNode expressionNode;

    public ExpressionStatementNode(Location location) {
        super(location);
        expressionNode = null;
    }

    public ExpressionStatementNode(Location location, ExpressionNode expressionNode){
        super(location);
        this.expressionNode = expressionNode;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }
}
