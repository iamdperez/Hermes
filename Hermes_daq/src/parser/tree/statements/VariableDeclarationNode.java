package parser.tree.statements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.symbolsTable.VarSymbol;
import parser.tree.types.PinType;
import parser.tree.types.Type;

import java.util.ArrayList;

public class VariableDeclarationNode extends StatementNode {
    private final ArrayList<IdNode> idList;

    public VariableDeclarationNode(Location location, ArrayList<IdNode>  idList) {
        super(location);
        this.idList = idList;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        for (IdNode item: idList) {
            if(SymbolsTable.getInstance().variableExist(item.getName()))
                throw new SemanticException(
                        ParserUtils.getInstance().getLineErrorMessage("Variable `"+item.getName()+"` was declared before",
                                item.getLocation()));
            SymbolsTable.getInstance().declareVariable(item.getName(),new VarSymbol());
            item.setType(ParserUtils.getInstance().getTypeNode(ParserUtils.intType));
        }
    }

    @Override
    public void interpret() {

    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }
}
