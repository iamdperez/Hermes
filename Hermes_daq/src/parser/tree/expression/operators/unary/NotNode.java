package parser.tree.expression.operators.unary;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.UnaryOperator;
import serialCommunication.SerialCommException;

public class NotNode extends UnaryOperator {
    public NotNode(Location location, ExpressionNode expression) {
        super(location, expression);
    }

    public NotNode(Location location){
        super(location);
    }

    @Override
    public Value interpret() throws SemanticException, SerialCommException {
        if(getExpression() instanceof IdNode){
            IdNode id = (IdNode)getExpression();
            Value value = SymbolsTable.getInstance().getVariableValue(id.getName(), id.getLocation());
            int result = ~(int)value.getValue();
            return new IntValue(result);
        }else{
            Value expVal = getExpression().interpret();
            int result = ~(int)expVal.getValue();
            return new IntValue(result);
        }
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type expressionType = getExpression().evaluateSemantic();

        validateIdNode(getExpression());

        if(nodeIsValid(expressionType))
            return expressionType;
        throw new SemanticException(
                ParserUtils.getInstance()
                        .getLineErrorMessage("Not is not supported `"+expressionType
                                +"`, must be a IntType",getLocation())
        );
    }


}
