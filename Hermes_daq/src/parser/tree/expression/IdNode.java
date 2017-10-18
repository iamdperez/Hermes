package parser.tree.expression;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
import parser.tree.interfaces.Variable;

public class IdNode extends ExpressionNode implements Variable {
    private final String name;

    public IdNode(Location location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public Value Interpret() {
        return null;
    }

    @Override
    public Type EvaluateSemantic() {
        return null;
    }

    public String getName() {
        return name;
    }
}
