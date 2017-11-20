package parser.tree.statements.globalVariables;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.PinSymbol;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.PinType;
import parser.tree.values.IntValue;

import java.util.ArrayList;

public class PinDeclarationNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final ArrayList<Integer> pinList;
    public PinDeclarationNode(Location location, ArrayList<IdNode> idList,
                              ArrayList<Integer> pinList) {
        super(location);
        this.idList = idList;
        this.pinList = pinList;

        for (IdNode item: this.idList) {
            item.setType(ParserUtils.pinType);
        }
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(idList.size() != pinList.size()) {
            throw new SemanticException(
                    getLineErrorMessage("Id list must have the same size than " +
                            "pin list."));
        }
        for (int i =0; i < idList.size(); i++) {
            IdNode item = idList.get(i);
            if(SymbolsTable.getInstance().variableExist(item.getName())){
                throw new SemanticException(
                        getLineErrorMessage("Variable `"+item.getName()+"` was declared before"));
            }else{
                SymbolsTable.getInstance().declareVariable(item.getName(), new PinSymbol(pinList.get(i)));
            }
        }
    }

    @Override
    public void interpret() {

    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }

    public ArrayList<Integer> getPinList() {
        return pinList;
    }
}
