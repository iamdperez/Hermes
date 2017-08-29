package serialCommunication;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class SerialCommunication {
    private OutputStream _outputStream;
    private InputStream _inputStram;
    private SerialPort _serialPort;

    public SerialCommunication(){
        _serialPort =  SerialPort.getCommPorts()[0];
        _serialPort.openPort();
        _serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
                100, 0);
        _outputStream = _serialPort.getOutputStream();
        _inputStram = _serialPort.getInputStream();
    }

    private byte[] sendCommand(byte command, int pinNumber) throws IOException {
        byte[] message = new byte[]{
                Commands.COMMAND_HEADER,
                command, (byte)pinNumber,
                Commands.COMMAND_TRAILER };
        _outputStream.write(message);
        int incomingAvailable = _inputStram.available();
        byte [] response = new byte[incomingAvailable];
        _inputStram.read(response);
        return response;
    }

    public String getArduinoModel() throws IOException {
        byte [] model = sendCommand(Commands.GET_ARDUINO_MODEL, 0);
        String modelBoard = "";
        for (byte b:model) {
            modelBoard += (char)b;
        }
        return modelBoard;
    }

    public boolean close(){
        return _serialPort.closePort();
    }

    public void finalize(){
        _outputStream = null;
        _inputStram = null;
        _serialPort = null;
    }
}
