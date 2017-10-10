package parser.tree.expression.operators.reduction;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;
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
    public Value Interpret() {
        return null;
    }

    @Override
    public Type EvaluateSemantic() {
        return null;
    }

    public ArrayList<IdNode> getIds() {
        return ids;
    }
}
