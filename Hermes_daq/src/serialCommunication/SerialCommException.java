package serialCommunication;

public class SerialCommException extends Exception {
    public SerialCommException() { super(); }
    public SerialCommException(String message) { super(message); }
    public SerialCommException(String message, Throwable cause) { super(message, cause); }
    public SerialCommException(Throwable cause) { super(cause); }
}
