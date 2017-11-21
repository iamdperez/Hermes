package parser.tree.expression.operators.unary;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class PosIncrementOperatorNode extends UnaryOperator {
    public PosIncrementOperatorNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    @Override
    public Value interpret() throws SemanticException {
        IdNode id = (IdNode)getExpression();
        Value idValue = SymbolsTable.getInstance().getVariableValue(id.getName(), id.getLocation());
        Value returnValue = idValue.clone();
        int value = (int)idValue.getValue() + 1;
        idValue.setValue(value);
        SymbolsTable.getInstance().setVariableValue(id.getName(),idValue);
        return  returnValue;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return validatePosOrPreOperation("Post Increment");
    }
}
