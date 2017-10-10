package parser.tree.expression.operators;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;

public class TernaryOperatorNode extends ExpressionNode {
    private final ExpressionNode truePart;
    private final ExpressionNode condition;
    private final ExpressionNode falsePart;

    public TernaryOperatorNode(Location location, ExpressionNode condition, ExpressionNode truePart, ExpressionNode falsePart) {
        super(location);
        this.truePart = truePart;
        this.condition = condition;
        this.falsePart = falsePart;
    }

    @Override
    public Value Interpret() {
        return null;
    }

    @Override
    public Type EvaluateSemantic() {
        return null;
    }

    public ExpressionNode getTruePart() {
        return truePart;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ExpressionNode getFalsePart() {
        return falsePart;
    }
}
