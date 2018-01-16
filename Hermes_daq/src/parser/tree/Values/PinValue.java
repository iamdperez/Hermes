package parser.tree.values;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.symbolsTable.SymbolsTable;
import serialCommunication.Command;
import serialCommunication.SerialCommException;
import serialCommunication.SerialCommunication;

public class PinValue extends Value {
    private int value;
    private String variableRef;
    public PinValue(int value, String variableRef) {
        super(value);
        this.value = value > 0 ? 1 : 0;
        this.variableRef = variableRef;
    }

    @Override
    public Value clone() {
        try {
            return new PinValue((int)this.getValue(),variableRef);
        } catch (SemanticException e) {
            e.printStackTrace();
        } catch (SerialCommException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getValue() throws SemanticException, SerialCommException {
        if(variableRef.equals("HIGH")){
            value = 1;
        }else if(variableRef.equals("LOW")){
            value = 0;
        }else if(ParserUtils.getInstance().getParserSettings().isAvailableSerialCommunication()){
            int pinNumber = SymbolsTable.getInstance().getPinNumber(variableRef);
            boolean v = SerialCommunication.getInstance().getValue(pinNumber);
            value = v ? 1 : 0;
        }
        return value;
    }

    @Override
    public void setValue(Object value) throws SemanticException, SerialCommException {
        this.value = (int)value > 0 ? 1 : 0;
        ParserUtils.getInstance().executeValueEvent(variableRef, this.value > 0);
        if(ParserUtils.getInstance().getParserSettings().isAvailableSerialCommunication()){
            int pinNumber = SymbolsTable.getInstance().getPinNumber(variableRef);
            Command command = this.value == 0 ? Command.SET_VALUE_LOW : Command.SET_VALUE_HIGH;
            SerialCommunication.getInstance().setValue(command,pinNumber);
        }
    }

    @Override
    public String toString() {
        //return Integer.toString(value);
        return value == 0 ? "LOW" : "HIGH";
    }
}
