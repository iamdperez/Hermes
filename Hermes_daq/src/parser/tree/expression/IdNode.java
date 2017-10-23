package parser.tree.expression;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.interfaces.Variable;

public class IdNode extends ExpressionNode implements Variable {
    private final String name;
    private Type type;
    public IdNode(Location location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public Value interpret() {
        return null;
    }

    @Override
    public Type evaluateSemantic() {
        return null;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
