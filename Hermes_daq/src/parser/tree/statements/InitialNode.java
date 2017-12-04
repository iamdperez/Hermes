package parser.tree.statements;

import parser.ParserUtils;
import parser.parserSettings.DeviceInfo;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.statements.globalVariables.GlobalVariablesNode;
import parser.tree.symbolsTable.SymbolsTable;
import serialCommunication.SerialCommException;
import serialCommunication.SerialCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void validateSemantic() throws SemanticException, SerialCommException {
        ArrayList<DeviceInfo> devicesInfo = ParserUtils.getInstance().getParserSettings().getDeviceInfoArrayList();
        List<DeviceInfo> dFiltered =  devicesInfo.stream().filter(o -> o.getDeviceName().equals(deviceModel))
                .collect(Collectors.toList());
        if(dFiltered.size() <= 0){
            throw new SemanticException(ParserUtils.getInstance()
                    .getLineErrorMessage("Device model is not supported for the platform",getLocation()));
        }
        SymbolsTable.getInstance().setDeviceInfo(dFiltered.get(0));
        if(ParserUtils.getInstance().getParserSettings().isAvailableSerialCommunication()){
            String device = SerialCommunication.getInstance().getArduinoModel();
            if(device == null || !device.equals(SymbolsTable.getInstance().getDeviceInfo().getDeviceName())){
                throw new SemanticException(ParserUtils.getInstance()
                        .getLineErrorMessage("Device model is not supported for the platform",getLocation()));
            }
        }
        for(GlobalVariablesNode item: GlobalVariables){
            item.validateSemantic();
        }
    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
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
