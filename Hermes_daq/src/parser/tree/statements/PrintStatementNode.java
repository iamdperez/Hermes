package parser.tree.statements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.types.StringType;

public class PrintStatementNode extends StatementNode {
    private final ExpressionNode expression;

    public PrintStatementNode(Location location, ExpressionNode expression) {
        super(location);
        this.expression = expression;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(!(getExpression().evaluateSemantic() instanceof StringType))
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Expression must be type of `StringType`",
                            getLocation()));
    }

    @Override
    public void interpret() throws SemanticException {
        System.out.println(getExpression().interpret().getValue());
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
