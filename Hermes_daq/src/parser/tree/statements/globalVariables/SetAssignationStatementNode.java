package parser.tree.statements.globalVariables;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.IdNode;
import parser.tree.types.IntType;
import parser.tree.types.Type;

import java.util.ArrayList;

public class SetAssignationStatementNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final ExpressionNode expression;

    public SetAssignationStatementNode(Location location, ArrayList<IdNode> idList,
                                       ExpressionNode expressionNode) {
        super(location);
        this.idList = idList;
        this.expression = expressionNode;

        for (IdNode item: this.idList) {
            item.setType(ParserUtils.pinType);
        }

    }

    @Override
    public void validateSemantic() throws SemanticException {
        validateSetIdList(idList);
        Type et = expression.evaluateSemantic();
        if(!(et instanceof IntType))
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Expected `IntType` in expression", expression.getLocation()));
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
