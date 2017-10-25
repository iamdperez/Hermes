package parser.tree.expression.operators.reduction;

import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.IdNode;
import java.util.ArrayList;

public class AndReductionNode extends ExpressionNode {
    private final ArrayList<IdNode> ids;

    public AndReductionNode(Location location, ArrayList<IdNode> ids) {
        super(location);
        this.ids = ids;
    }

    @Override
    public Value interpret() {
        return null;
    }

    @Override
    public Type evaluateSemantic() {
        return null;
    }

    public ArrayList<IdNode> getIds() {
        return ids;
    }
}
