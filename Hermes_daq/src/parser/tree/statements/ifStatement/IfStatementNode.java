package parser.tree.statements.ifStatement;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class IfStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final ArrayList<ElseIfNode> elseIfNodes;
    private final ElseNode elseNode;
    private final ArrayList<StatementNode> statementList;

    public IfStatementNode(Location location, ExpressionNode condition,
                           ArrayList<StatementNode> statementsList,
                           ArrayList<ElseIfNode> elseIfNodes, ElseNode elseNode) {
        super(location);
        this.condition = condition;
        this.statementList = statementsList;
        this.elseIfNodes = elseIfNodes;
        this.elseNode = elseNode;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ArrayList<ElseIfNode> getElseIfNodes() {
        return elseIfNodes;
    }

    public ElseNode getElseNode() {
        return elseNode;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
