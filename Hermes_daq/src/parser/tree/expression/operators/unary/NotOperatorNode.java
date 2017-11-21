package parser.tree.expression.operators.unary;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.IntType;
import parser.tree.types.PinType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;

public class NotOperatorNode extends UnaryOperator {
    public NotOperatorNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    public NotOperatorNode(Location location){
        super(location );
    }

    @Override
    public Value interpret() throws SemanticException {
        if(getExpression() instanceof IdNode){
            IdNode id = (IdNode)getExpression();
            Value value = SymbolsTable.getInstance().getVariableValue(id.getName());
            int result = (int)value.getValue() == 0 ? 1 :0;
            return new IntValue(result);
        }
        Value valueExp = getExpression().interpret();
        int resultVal = (int)valueExp.getValue() == 0 ? 1: 0;
        return new IntValue(resultVal);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type expressionType = getExpression().evaluateSemantic();

        validateIdNode(getExpression());

        if(nodeIsValid(expressionType))
            return expressionType;
        throw new SemanticException(
                ParserUtils.getInstance()
                        .getLineErrorMessage("Not operator is not supported `"+expressionType
                                +"`, must be a PinType",getLocation()));
    }

    @Override
    protected boolean nodeIsValid(Type t){
        return t instanceof PinType || t instanceof IntType;
    }
}
