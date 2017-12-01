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
import parser.tree.values.Value;

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
    public void validateSemantic() throws SemanticException {
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
        if(getElseIfNodes() != null){
            for (ElseIfNode item: elseIfNodes) {
                item.validateSemantic();
            }
        }
        if(getElseNode() != null)
            elseNode.validateSemantic();
    }

    private boolean typeIsValid(Type t) {
        return t instanceof IntType
                || t instanceof PinType
                || t instanceof SetType;
    }

    @Override
    public void interpret() throws SemanticException {
        Value conditionV = condition.interpret();
        if((int)conditionV.getValue()!=0){
            for (StatementNode item: statementList) {
                item.interpret();
            }
        }else{
            boolean elseIfExecuted = false;
            if(getElseIfNodes()!=null){
                for(int i =0; i < elseIfNodes.size();i++){
                    Value elseIfCondition = elseIfNodes.get(i).getCondition().interpret();
                    if((int)elseIfCondition.getValue() == 0)
                        continue;
                    elseIfNodes.get(i).interpret();
                    elseIfExecuted = true;
                    break;
                }
            }

            if(elseNode !=null && !elseIfExecuted)
                elseNode.interpret();
        }
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
