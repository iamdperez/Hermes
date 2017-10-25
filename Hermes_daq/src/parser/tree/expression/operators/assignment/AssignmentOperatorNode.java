package parser.tree.expression.operators.assignment;

import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.interfaces.Variable;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.AssignmentOperator;

public class AssignmentOperatorNode extends AssignmentOperator {

    public AssignmentOperatorNode(Location location){
        super(location);
    }

    public AssignmentOperatorNode(Location location, Variable variable, ExpressionNode rightNode) {
        super(location, variable, rightNode);
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
