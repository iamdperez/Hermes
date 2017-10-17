package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.expression.IdNode;

import java.util.ArrayList;

public class PinDefinitionNode extends GlobalVariablesNode {
    private final ArrayList<IdNode> idList;
    private final PinType pinType;

    public PinDefinitionNode(Location location, ArrayList<IdNode> idList,
                             PinType pinType) {
        super(location);
        this.idList = idList;
        this.pinType = pinType;
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

    public PinType getPinType() {
        return pinType;
    }
}
