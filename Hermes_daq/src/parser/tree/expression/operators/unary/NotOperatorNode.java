package parser.tree.expression.operators.unary;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class NotOperatorNode extends UnaryOperator {
    public NotOperatorNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    public NotOperatorNode(Location location){
        super(location );
    }

    @Override
    public Value interpret() {
        return null;
    }

    @Override
    public Type evaluateSemantic() {
        return null;
    }
}
