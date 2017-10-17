package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.expression.IdNode;

import java.util.ArrayList;

public class PinDeclarationNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final ArrayList<Integer> pinList;

    public PinDeclarationNode(Location location, ArrayList<IdNode> idList,
                              ArrayList<Integer> pinList) {
        super(location);
        this.idList = idList;
        this.pinList = pinList;
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

    public ArrayList<Integer> getPinList() {
        return pinList;
    }
}
