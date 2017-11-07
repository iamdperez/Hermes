package parser.tree.statements.globalVariables;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.PinType;
import parser.tree.types.Type;

import java.util.ArrayList;

public class PinDefinitionNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final IO IO;

    public PinDefinitionNode(Location location, ArrayList<IdNode> idList,
                             IO IO) {
        super(location);
        this.idList = idList;
        this.IO = IO;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        for (IdNode item: idList) {
            if(!SymbolsTable.getInstance().variableExist(item.getName()))
                throw new SemanticException(
                        getLineErrorMessage("Variable `"+item.getName()+"` must be declared before"));
            Type varType = SymbolsTable.getInstance().getVariableType(item.getName());
            if(!(varType instanceof PinType))
                throw new SemanticException(
                        getLineErrorMessage("Variable `"+item.getName()+"` must be type of `PinType`"));
            SymbolsTable.getInstance().setPinIO(item.getName(), IO);
        }
    }

    @Override
    public void interpret() {

    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }

    public IO getIO() {
        return IO;
    }
}
