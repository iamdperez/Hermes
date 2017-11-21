package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.PinType;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.Value;

public class PinSymbol implements Symbol {
    private PinValue value;
    private final int pinNumber;
    private IO pinIO;

    public PinSymbol(int pinNumber){
        this.pinNumber = pinNumber;
    }
    @Override
    public Type getType() {
        return ParserUtils.pinType;
    }

    @Override
    public Value getDefaultValue() {
        value = new PinValue();
        return value;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = (PinValue)value;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinIO(IO pinIO) {
        this.pinIO = pinIO;
    }

    public IO getPinIO(){
        return pinIO;
    }
}
