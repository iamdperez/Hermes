package parser.tree.statements.ifStatement;

import parser.tree.Location;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class ElseNode {
    private final Location location;
    private final ArrayList<StatementNode> statementList;

    public ElseNode(Location location, ArrayList<StatementNode> statementList){
        this.location = location;
        this.statementList = statementList;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
