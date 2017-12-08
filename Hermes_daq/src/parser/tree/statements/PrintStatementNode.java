package parser.tree.statements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.types.*;
import serialCommunication.SerialCommException;

public class PrintStatementNode extends StatementNode {
    private final ExpressionNode expression;

    public PrintStatementNode(Location location, ExpressionNode expression) {
        super(location);
        this.expression = expression;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(!(typeIsValid(getExpression().evaluateSemantic())))
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Expression must be type of `StringType`",
                            getLocation()));
    }

    private boolean typeIsValid(Type t){
        return (t instanceof StringType)
                || (t instanceof IntType)
                || (t instanceof SetType)
                || (t instanceof PinType);
    }


    @Override
    public void interpret() throws SemanticException, SerialCommException {
        if(ParserUtils.getInstance().getParserSettings().isAvailableUiConsole()){
            ParserUtils.getInstance().getUiConsole().appendText("\r\n"+getExpression().interpret().getValue());
        }else{
            System.out.println(getExpression().interpret().getValue());
        }
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
