package parser.tree.statements;

import parser.tree.Location;
import parser.tree.expression.IdNode;

import java.util.ArrayList;

public class VariableDeclarationNode extends StatementNode {
    private final ArrayList<IdNode> idList;

    public VariableDeclarationNode(Location location, ArrayList<IdNode>  idList) {
        super(location);
        this.idList = idList;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public ArrayList<IdNode> getIdList() {
        return idList;
    }
}
