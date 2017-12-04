package parser.tree.statements.ifStatement;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.StatementNode;
import parser.tree.types.IntType;
import parser.tree.types.PinType;
import parser.tree.types.SetType;
import parser.tree.types.Type;
import serialCommunication.SerialCommException;

import java.util.ArrayList;

public class ElseIfNode extends StatementNode {
    private final ExpressionNode condition;
    private final ArrayList<StatementNode> statementList;

    public ElseIfNode(Location location, ExpressionNode condition, ArrayList<StatementNode> statementList){
        super(location);
        this.condition = condition;
        this.statementList = statementList;
    }

    @Override
    public void validateSemantic() throws SemanticException, SerialCommException {
        Type conditionT = getCondition().evaluateSemantic();
        if(!(typeIsValid(conditionT))){
            throw new SemanticException(
                    ParserUtils.getInstance()
                            .getLineErrorMessage("Condition expression must be `IntType, SetType, PinType` not `"
                                    +conditionT+"`. " ,condition.getLocation())
            );
        }
        for (StatementNode item: statementList) {
            item.validateSemantic();
        }
    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
        for (StatementNode item: statementList) {
            item.interpret();
        }
    }

    private boolean typeIsValid(Type t) {
        return t instanceof IntType
                || t instanceof PinType
                || t instanceof SetType;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
