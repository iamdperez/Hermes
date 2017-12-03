package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import serialCommunication.SerialCommException;

public class ExpressionStatementNode extends StatementNode {
    private final ExpressionNode expressionNode;

    public ExpressionStatementNode(Location location) {
        super(location);
        expressionNode = null;
    }

    public ExpressionStatementNode(Location location, ExpressionNode expressionNode){
        super(location);
        this.expressionNode = expressionNode;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        expressionNode.evaluateSemantic();
    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
        expressionNode.interpret();
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }
}
