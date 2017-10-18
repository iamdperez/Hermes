package parser.tree.statements.jumpStatements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

public class ReturnNode extends JumpStatementNode {
    private final ExpressionNode expression;

    public ReturnNode(Location location) {
        super(location);
        expression = null;
    }

    public ReturnNode(Location location, ExpressionNode expressionNode){
        super(location);
        this.expression = expressionNode;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
