package parser.tree.expression.operators;

import parser.tree.Location;
import parser.tree.interfaces.Variable;
import parser.tree.expression.ExpressionNode;

public abstract class AssignmentOperator extends ExpressionNode {
    private Variable variable;
    private ExpressionNode rightNode;
     public AssignmentOperator(Location location){
         super(location);
     }
    public AssignmentOperator(Location location, Variable variable, ExpressionNode rightNode){
        super(location);
        this.variable = variable;
        this.rightNode = rightNode;
    }
    public Variable getVariable() {
        return variable;
    }

    public ExpressionNode getRightNode() {
        return rightNode;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public void setRightNode(ExpressionNode rightNode) {
        this.rightNode = rightNode;
    }
}
