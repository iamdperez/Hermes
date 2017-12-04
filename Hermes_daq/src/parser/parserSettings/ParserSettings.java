package parser.parserSettings;

import java.util.ArrayList;

public class ParserSettings {
    public ParserSettings(){}

    public boolean isAvailableSerialCommunication() {
        return availableSerialCommunication;
    }

    public void setAvailableSerialCommunication(boolean availableSerialCommunication) {
        this.availableSerialCommunication = availableSerialCommunication;
    }

    public boolean isAvailableUiConsole() {
        return availableUiConsole;
    }

    public void setAvailableUiConsole(boolean availableUiConsole) {
        this.availableUiConsole = availableUiConsole;
    }

    public ArrayList<DeviceInfo> getDeviceInfoArrayList() {
        return deviceInfoArrayList;
    }

    public void setDeviceInfoArrayList(ArrayList<DeviceInfo> deviceInfoArrayList) {
        this.deviceInfoArrayList = deviceInfoArrayList;
    }

    private boolean availableSerialCommunication;
    private boolean availableUiConsole;
    private ArrayList<DeviceInfo> deviceInfoArrayList;
}
