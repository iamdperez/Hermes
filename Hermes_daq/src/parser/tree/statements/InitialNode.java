package parser.tree.statements;

import parser.tree.Location;
import parser.tree.statements.globalVariables.GlobalVariablesNode;

import java.util.ArrayList;

public class InitialNode extends StatementNode {
    private final String deviceModel;
    private final ArrayList<GlobalVariablesNode> GlobalVariables;

    public InitialNode(Location location, String deviceModel,
                       ArrayList<GlobalVariablesNode> globalVariables){
        super(location);
        this.deviceModel = deviceModel;
        this.GlobalVariables = globalVariables;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public ArrayList<GlobalVariablesNode> getGlobalVariables() {
        return GlobalVariables;
    }
}
