package parser.tree.statements.iterationStatements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.interfaces.RelationalOperator;
import parser.tree.statements.ExpressionStatementNode;
import parser.tree.statements.StatementNode;
import parser.tree.symbolsTable.Looping;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;

import java.util.ArrayList;

public class ForStatementNode extends IterationStatementNode {
    private final ExpressionStatementNode firstExpression;
    private final ExpressionNode secondExpression;
    private final ArrayList<StatementNode> statementList;

    public ExpressionStatementNode getFirstExpression() {
        return firstExpression;
    }

    public ExpressionNode getSecondExpression() {
        return secondExpression;
    }

    public ExpressionNode getThirdExpression() {
        return thirdExpression;
    }

    private final ExpressionNode thirdExpression;

    public ForStatementNode(Location location, ExpressionStatementNode firstExpression,
                            ExpressionNode secondExpression, ExpressionNode thirdExpression,
                            ArrayList<StatementNode> statementList) {
        super(location);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.statementList = statementList;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        firstExpression.validateSemantic();
        Type secondExp = secondExpression.evaluateSemantic();
        if(!(secondExpression instanceof RelationalOperator)){
            throw new SemanticException(
                    ParserUtils.getInstance()
                            .getLineErrorMessage("Second expression in for statement must be a relational",
                                    secondExpression.getLocation())
            );
        }

        if(!(secondExp instanceof IntType)){
            throw new SemanticException(
                    ParserUtils.getInstance()
                            .getLineErrorMessage("Second expression in for statement must be an IntType",
                                    secondExpression.getLocation())
            );
        }

        Looping l = new Looping();
        SymbolsTable.getInstance().pushLooping(l);
        SymbolsTable.getInstance().pushNewContext();
        for (StatementNode item: statementList) {
            item.validateSemantic();
        }
        SymbolsTable.getInstance().popContext();
        SymbolsTable.getInstance().popLooping();
        thirdExpression.evaluateSemantic();
    }

    @Override
    public void interpret() throws SemanticException {
        firstExpression.interpret();
        IntValue condition = (IntValue) secondExpression.interpret();
        Looping l = new Looping();
        SymbolsTable.getInstance().pushLooping(l);
        while ((int)condition.getValue() != 0 && l.looping){
            SymbolsTable.getInstance().pushNewContext();
            for (StatementNode item: statementList) {
                item.interpret();
                if(!l.looping)
                    break;
            }
            SymbolsTable.getInstance().popContext();
            thirdExpression.interpret();
            condition = (IntValue)secondExpression.interpret();
        }
        l.looping = false;
        SymbolsTable.getInstance().popLooping();
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }
}
