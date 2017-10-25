package parser.tree.expression.operators.unary;

import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.operators.UnaryOperator;

public class MinusNode extends UnaryOperator {
    public MinusNode(Location location) {
        super(location);
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
