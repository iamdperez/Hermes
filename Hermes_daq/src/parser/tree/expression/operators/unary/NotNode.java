package parser.tree.expression.operators.unary;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class NotNode extends UnaryOperator {
    public NotNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    public NotNode(Location location){
        super(location);
    }

    @Override
    public Value Interpret() {
        return null;
    }

    @Override
    public Type EvaluateSemantic() {
        return null;
    }
}