package parser.tree.expression.operators;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

public abstract class BinaryOperator extends ExpressionNode {
    private ExpressionNode leftNode;
    private ExpressionNode rightNode;

    public BinaryOperator(Location location, ExpressionNode leftNode, ExpressionNode rightNode) {
        super(location);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public ExpressionNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(ExpressionNode leftNode) {
        this.leftNode = leftNode;
    }

    public ExpressionNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(ExpressionNode rightNode) {
        this.rightNode = rightNode;
    }
}
