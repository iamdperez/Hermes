package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.tree.expression.IdNode;
import parser.tree.interfaces.Symbol;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.Value;

public class PinSymbol implements Symbol {
    private PinValue value;
    private final int pinNumber;
    private IO pinIO;
    private String variableRef;
    public PinSymbol(int pinNumber, String variableRef){
        this.pinNumber = pinNumber;
        this.variableRef = variableRef;
    }
    @Override
    public Type getType() {
        return ParserUtils.pinType;
    }

    @Override
    public Value getDefaultValue() {
        value = new PinValue(0,variableRef);
        return value;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = (PinValue)value;
//        ParserUtils.getInstance().executeValueEvent(variableRef,true);
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
