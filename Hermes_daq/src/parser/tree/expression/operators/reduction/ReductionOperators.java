package parser.tree.expression.operators.reduction;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.PinType;
import parser.tree.types.Type;

import java.util.ArrayList;

public abstract class ReductionOperators extends ExpressionNode {
    public ReductionOperators(Location location) {
        super(location);
    }

    protected Type validateSemantic(String operator, ArrayList<IdNode> ids) throws SemanticException {
        for (IdNode item: ids) {
            if(!SymbolsTable.getInstance().variableExist(item.getName())) {
                throw new SemanticException(
                        ParserUtils.getInstance()
                                .getLineErrorMessage("Variable `" + item.getName()
                                        +"` must be declared before in "+operator+" reduction operator.",
                                        getLocation()));
            }
            Type variableType = SymbolsTable.getInstance().getVariableType(item.getName());
            if(!(variableType instanceof PinType)) {
                throw new SemanticException(
                        ParserUtils.getInstance()
                                .getLineErrorMessage("Variable `" + item.getName()
                                        + "` must be type of `PinType` in "+operator+" reduction operator.",
                                        getLocation()));
            }
        }
        return  ParserUtils.intType;
    }
}
