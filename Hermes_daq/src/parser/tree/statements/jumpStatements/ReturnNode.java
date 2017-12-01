package parser.tree.statements.jumpStatements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.symbolsTable.FunctionCalled;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;

public class ReturnNode extends JumpStatementNode {
    private final ExpressionNode expression;

    public ReturnNode(Location location) {
        super(location);
        expression = null;
    }

    public ReturnNode(Location location, ExpressionNode expressionNode){
        super(location);
        this.expression = expressionNode;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(getExpression() == null) return;
        Type t = expression.evaluateSemantic();
        if(!(t instanceof IntType))
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Return value must be IntType not"+t,
                            expression.getLocation())
            );
    }

    @Override
    public void interpret() throws SemanticException {
        FunctionCalled f = SymbolsTable.getInstance().getCurrentFunctionCalled();
        if(expression == null){
            SymbolsTable.getInstance().setVariableValue(f.functionName,ParserUtils.intValue);
        }else {
            SymbolsTable.getInstance().setVariableValue(f.functionName,expression.interpret());
        }
        f.called = false;
        SymbolsTable.getInstance().stopAllLoops();
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
