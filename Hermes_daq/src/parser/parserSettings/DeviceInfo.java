package parser.parserSettings;

public class DeviceInfo {
    public DeviceInfo(){
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getMinPin() {
        return minPin;
    }

    public void setMinPin(int minPin) {
        this.minPin = minPin;
    }

    public int getMaxPin() {
        return maxPin;
    }

    public void setMaxPin(int maxPin) {
        this.maxPin = maxPin;
    }

    private String deviceName;
    private int minPin;
    private int maxPin;
}
