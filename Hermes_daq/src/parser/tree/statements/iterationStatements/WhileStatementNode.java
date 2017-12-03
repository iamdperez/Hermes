package parser.tree.statements.iterationStatements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.statements.StatementNode;
import parser.tree.symbolsTable.Looping;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.IntType;
import parser.tree.types.PinType;
import parser.tree.types.SetType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import serialCommunication.SerialCommException;

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
    public void validateSemantic() throws SemanticException, SerialCommException {
        Type condition = getCondition().evaluateSemantic();
        if(!(typeIsValid(condition))){
            throw new SemanticException(
                    ParserUtils.getInstance()
                            .getLineErrorMessage("Condition expression must be IntType not "
                                    +condition,getCondition().getLocation())

            );
        }
        Looping l = new Looping();
        SymbolsTable.getInstance().pushLooping(l);
        SymbolsTable.getInstance().pushNewContext();
        for(StatementNode item: statementList){
            item.validateSemantic();
        }
        SymbolsTable.getInstance().popContext();
        SymbolsTable.getInstance().popLooping();
    }

    private boolean typeIsValid(Type t) {
        return t instanceof IntType
                || t instanceof SetType
                || t instanceof PinType;
    }

    @Override
    public void interpret() throws SemanticException {
        Value condition = getCondition().interpret();
        Looping l = new Looping();
        SymbolsTable.getInstance().pushLooping(l);
        while ((int)condition.getValue() != 0 && l.looping){
            SymbolsTable.getInstance().pushNewContext();
            for(StatementNode item: statementList){
                item.interpret();
                if(!l.looping)
                    break;
            }
            SymbolsTable.getInstance().popContext();
            condition = getCondition().interpret();
        }
        l.looping = false;
        SymbolsTable.getInstance().popLooping();
    }
}
