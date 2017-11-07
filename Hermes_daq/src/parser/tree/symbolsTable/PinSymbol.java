package parser.tree.symbolsTable;

import parser.tree.statements.globalVariables.IO;
import parser.tree.types.PinType;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.Value;

public class PinSymbol implements Symbol {
    private final int pinNumber;
    private IO pinIO;

    public PinSymbol(int pinNumber){
        this.pinNumber = pinNumber;
    }
    @Override
    public Type getType() {
        return new PinType();
    }

    @Override
    public Value getDefaultValue() {
        return new PinValue();
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
