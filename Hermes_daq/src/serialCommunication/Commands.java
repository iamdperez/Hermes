package serialCommunication;

public class Commands {
    public static final byte PIN_MODE_INPUT = 0x10;
    public static final byte PIN_MODE_OUTPUT = 0x11;
    public static final byte SET_VALUE_HIGH = 0x20;
    public static final byte SET_VALUE_LOW = 0x21;
    public static final byte GET_VALUE = 0x30;
    public static final byte GET_ARDUINO_MODEL = 0x40;
    protected static final byte COMMAND_HEADER = 0x7f;
    protected static final byte COMMAND_TRAILER = 0x7e;
}
