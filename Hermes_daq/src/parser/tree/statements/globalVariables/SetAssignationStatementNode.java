package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.IdNode;

import java.util.ArrayList;

public class SetAssignationStatementNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final ExpressionNode expression;

    public SetAssignationStatementNode(Location location, ArrayList<IdNode> idList,
                                       ExpressionNode expressionNode) {
        super(location);
        this.idList = idList;
        this.expression = expressionNode;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }
}
