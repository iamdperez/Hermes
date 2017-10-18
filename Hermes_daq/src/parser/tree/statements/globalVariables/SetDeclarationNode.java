package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.expression.IdNode;

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
    public void validateSemantic() {

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
