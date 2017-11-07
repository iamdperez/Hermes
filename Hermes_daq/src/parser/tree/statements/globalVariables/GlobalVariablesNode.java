package parser.tree.statements.globalVariables;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.statements.StatementNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.PinType;
import parser.tree.types.Type;

import java.util.ArrayList;

public abstract class GlobalVariablesNode extends StatementNode {
    public GlobalVariablesNode(Location location) {
        super(location);
    }
    protected void validateSetIdList(ArrayList<IdNode> idList) throws SemanticException {
        for (IdNode item: idList) {
            if(!SymbolsTable.getInstance().variableExist(item.getName()))
                throw new SemanticException(
                        getLineErrorMessage("Variable `"+item.getName()+"` must be declared before"));
            Type variableType = SymbolsTable.getInstance().getVariableType(item.getName());
            if(!(variableType instanceof PinType))
                throw new SemanticException(
                        getLineErrorMessage("Variable `"+item.getName()+"` must be type of `PinType`"));
        }
    }
}
