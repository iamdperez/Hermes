package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.interfaces.DeviceInfo;
import parser.tree.statements.globalVariables.GlobalVariablesNode;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InitialNode extends StatementNode {
    private final String deviceModel;
    private final ArrayList<GlobalVariablesNode> GlobalVariables;
    private final ArrayList<DeviceInfo> devicesInfo;

    public InitialNode(Location location, String deviceModel,
                       ArrayList<GlobalVariablesNode> globalVariables, ArrayList<DeviceInfo> devicesInfo){
        super(location);
        this.deviceModel = deviceModel;
        this.GlobalVariables = globalVariables;
        this.devicesInfo = devicesInfo;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        for(GlobalVariablesNode item: GlobalVariables){
            item.validateSemantic();
        }
    }

    @Override
    public void interpret() throws SemanticException {
        for(GlobalVariablesNode item: GlobalVariables){
            item.interpret();
        }
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public ArrayList<GlobalVariablesNode> getGlobalVariables() {
        return GlobalVariables;
    }
}
