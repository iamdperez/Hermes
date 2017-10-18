package parser.tree.statements.iterationStatements;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.ExpressionStatementNode;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class ForStatementNode extends IterationStatementNode {
    private final ExpressionStatementNode firstExpression;
    private final ExpressionStatementNode secondExpression;
    private final ArrayList<StatementNode> statementList;

    public ExpressionStatementNode getFirstExpression() {
        return firstExpression;
    }

    public ExpressionStatementNode getSecondExpression() {
        return secondExpression;
    }

    public ExpressionNode getThirdExpression() {
        return thirdExpression;
    }

    private final ExpressionNode thirdExpression;

    public ForStatementNode(Location location, ExpressionStatementNode firstExpression,
                            ExpressionStatementNode secondExpression, ExpressionNode thirdExpression,
                            ArrayList<StatementNode> statementList) {
        super(location);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.statementList = statementList;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
