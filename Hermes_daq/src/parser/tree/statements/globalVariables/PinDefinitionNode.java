package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.expression.IdNode;

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
    public void validateSemantic() {

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
