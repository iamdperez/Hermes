package parser.tree.statements.globalVariables;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SetSymbol;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.PinType;
import parser.tree.types.Type;

import java.util.ArrayList;

public class SetDeclarationNode extends GlobalVariablesNode {
    private final String setName;
    private final ArrayList<IdNode> idList;

    public SetDeclarationNode(Location location, String setName, ArrayList<IdNode> idList) {
        super(location);
        this.setName = setName;
        this.idList = idList;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(SymbolsTable.getInstance().variableExist(setName))
            throw new SemanticException(
                    getLineErrorMessage("Variable `"+setName+"` was declared before"));
        validateSetIdList(idList);
        SymbolsTable.getInstance().declareVariable(setName, new SetSymbol(idList));
    }

    @Override
    public void interpret() {

    }

    public String getSetName() {
        return setName;
    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }
}
