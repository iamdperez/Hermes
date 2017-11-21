package parser.tree.expression.operators.unary;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class PreDecrementOperatorNode extends UnaryOperator {
    public PreDecrementOperatorNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    @Override
    public Value interpret() throws SemanticException {
        IdNode id = (IdNode)getExpression();
        Value variableValue = SymbolsTable.getInstance().getVariableValue(id.getName());
        int value = (int)variableValue.getValue() - 1;
        variableValue.setValue(value);
        SymbolsTable.getInstance().setVariableValue(id.getName(),variableValue);
        return variableValue;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return validatePosOrPreOperation("Pre Decrement");
    }
}
