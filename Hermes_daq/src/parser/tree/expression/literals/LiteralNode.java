package parser.tree.expression.literals;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.expression.ExpressionNode;

public abstract class LiteralNode extends ExpressionNode {
    private Object value;

    public LiteralNode(Location location, Object value) {
        super(location);
        this.value = value;
    }

    public abstract Type getType();

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
