package serialCommunication;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

public final class SerialCommunication {
    private OutputStream _outputStream;
    private InputStream _inputStream;
    private SerialPort _serialPort;
    private static SerialCommunication instance;
    private final static int timeoutMillis = 30000;
    public static synchronized SerialCommunication getInstance() throws SerialCommException {
        if (instance == null) {
            instance =  new SerialCommunication();
            return instance;
        }
        return instance;
    }

    private SerialCommunication() throws SerialCommException {
        try{
            _serialPort =  SerialPort.getCommPorts()[0];
            _serialPort.openPort();
            _serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING,0, 0);
            _outputStream = _serialPort.getOutputStream();
            _inputStream = _serialPort.getInputStream();
        }catch (Exception e){
            throw new SerialCommException("No connected arduino device was detected.");
        }
    }

    private byte[] sendCommand(Command command, int pinNumber) throws SerialCommException{
        byte[] message = new byte[]{
                getCommandByte(Command.COMMAND_HEADER),
                getCommandByte(command),
                (byte)pinNumber,
                getCommandByte(Command.COMMAND_TRAILER) };
        try {
            _outputStream.write(message);
            int incomingAvailable = _inputStream.available();
            long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
            while (incomingAvailable == 0){
                Thread.sleep(20);
                incomingAvailable = _inputStream.available();
                if(System.currentTimeMillis() < maxTimeMillis && incomingAvailable == 0){
                    throw new SerialCommException("TIMEOUT: Is not possible send command `"+command+"` to arduino device.");
                }
             }
            byte [] response = new byte[incomingAvailable];
            _inputStream.read(response);
            return response;
        } catch (Exception e) {
            throw new SerialCommException(e.getMessage());
        }
    }

    public String getArduinoModel() throws SerialCommException {
        byte [] model = sendCommand(Command.GET_ARDUINO_MODEL, 0);
        return new String(model);
    }

    public void pinMode(Command pinModeType, int pinNumber) throws SerialCommException {
        byte [] result = sendCommand(pinModeType, pinNumber);
        if(result[0] == 3){
            throw new SerialCommException("Error with command `"+pinModeType+"` for pinNumber `"+pinNumber+"`");
        }
    }

    public void setValue(Command valueType, int pinNumber) throws SerialCommException {
        byte [] result = sendCommand(valueType, pinNumber);
        if(result[0] == 3){
            throw new SerialCommException("Error with command `"+valueType+"` for pinNumber `"+pinNumber+"`");
        }
    }

    public boolean getValue(int pinNumber) throws SerialCommException {
        byte [] result = sendCommand(Command.GET_VALUE, pinNumber);
        if(result[0] == 3){
            throw new SerialCommException("Error with command `GET_VALUE` for pinNumber `"+pinNumber+"`");
        }
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

    private byte getCommandByte(Command command){
        switch (command){
            case PIN_MODE_INPUT: return 0x10;
            case PIN_MODE_OUTPUT: return 0x11;
            case SET_VALUE_HIGH: return 0x20;
            case SET_VALUE_LOW: return  0x21;
            case GET_VALUE: return 0x30;
            case GET_ARDUINO_MODEL: return 0x40;
            case COMMAND_HEADER: return 0x7f;
            case COMMAND_TRAILER: return 0x7e;
        }
        throw new IllegalArgumentException("Expected a valid command por serialCommunication.");
    }
}
