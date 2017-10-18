package parser.tree.statements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;

import java.util.ArrayList;

public class IfStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final ArrayList<StatementNode> elseStatementsList;

    public ExpressionNode getCondition() {
        return condition;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }

    private final ArrayList<StatementNode> statementList;

    public IfStatementNode(Location location, ExpressionNode condition,
                           ArrayList<StatementNode> statementsList) {
        super(location);
        this.condition = condition;
        this.statementList = statementsList;
        elseStatementsList = null;
    }

    public IfStatementNode(Location location, ExpressionNode condition,
                           ArrayList<StatementNode> statementsList,
                           ArrayList<StatementNode> elseStatementsList) {
        super(location);
        this.condition = condition;
        this.statementList = statementsList;
        this.elseStatementsList = elseStatementsList;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ArrayList<StatementNode> getElseStatementsList() {
        return elseStatementsList;
    }
}
