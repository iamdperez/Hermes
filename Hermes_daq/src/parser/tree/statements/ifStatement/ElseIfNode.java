package parser.tree.statements.ifStatement;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class ElseIfNode {
    private final Location location;
    private final ExpressionNode condition;
    private final ArrayList<StatementNode> statementList;

    public ElseIfNode(Location location, ExpressionNode condition, ArrayList<StatementNode> statementList){
        this.location = location;
        this.condition = condition;
        this.statementList = statementList;
    }

    public Location getLocation() {
        return location;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
