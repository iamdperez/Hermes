package serialCommunication;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialCommunication {
    private OutputStream _outputStream;
    private InputStream _inputStream;
    private SerialPort _serialPort;

    public SerialCommunication(){
        _serialPort =  SerialPort.getCommPorts()[0];
        _serialPort.openPort();
        _serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING,0, 0);
        _outputStream = _serialPort.getOutputStream();
        _inputStream = _serialPort.getInputStream();
    }

    private byte[] sendCommand(byte command, int pinNumber) throws IOException, InterruptedException {
        byte[] message = new byte[]{
                Commands.COMMAND_HEADER,
                command, 
                (byte)pinNumber,
                Commands.COMMAND_TRAILER };
        _outputStream.write(message);
        int incomingAvailable = _inputStream.available();
        while (incomingAvailable == 0){
            Thread.sleep(20);
            incomingAvailable = _inputStream.available();
        }
        byte [] response = new byte[incomingAvailable];
        _inputStream.read(response);
        return response;
    }

    public String getArduinoModel() throws IOException, InterruptedException {
        byte [] model = sendCommand(Commands.GET_ARDUINO_MODEL, 0);
        return new String(model);
    }

    public void pinMode(byte pinModeType, int pinNumber) throws IOException, InterruptedException {
        byte [] result = sendCommand(pinModeType, pinNumber);
    }

    public void setValue(byte valueType, int pinNumber) throws IOException, InterruptedException {
        byte [] result = sendCommand(valueType, pinNumber);
    }

    public boolean getValue(int pinNumber) throws IOException, InterruptedException {
        byte [] result = sendCommand(Commands.GET_VALUE, pinNumber);
        return result[0] != 0;
    }

    public boolean close(){
        return _serialPort.closePort();
    }

    public void finalize(){
        _outputStream = null;
        _inputStream = null;
        _serialPort = null;
        close();
    }
}
