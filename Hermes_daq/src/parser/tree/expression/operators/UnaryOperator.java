package parser.tree.expression.operators;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

public abstract class UnaryOperator extends ExpressionNode {
    private final ExpressionNode expression;

    public UnaryOperator(Location location, ExpressionNode expression) {
        super(location);
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
