package parser.tree.expression;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;

public abstract class ExpressionNode {

    private final Location location;
    public ExpressionNode(Location location){
        this.location = location;
    }
    public abstract Value Interpret();
    public abstract Type EvaluateSemantic();
    public Location getLocation() {
        return location;
    }
}
