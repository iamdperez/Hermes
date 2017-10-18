package parser.tree.statements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

public class PrintStatementNode extends StatementNode {
    private final ExpressionNode expression;

    public PrintStatementNode(Location location, ExpressionNode expression) {
        super(location);
        this.expression = expression;
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
