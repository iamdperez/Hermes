package parser.tree.statements.iterationStatements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class WhileStatementNode extends IterationStatementNode {
    private final ExpressionNode condition;

    public ExpressionNode getCondition() {
        return condition;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }

    private final ArrayList<StatementNode> statementList;

    public WhileStatementNode(Location location, ExpressionNode condition,
                              ArrayList<StatementNode> statementList) {
        super(location);
        this.condition = condition;
        this.statementList = statementList;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }
}
