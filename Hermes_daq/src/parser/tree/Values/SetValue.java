package parser.tree.values;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SetSymbol;
import parser.tree.symbolsTable.SymbolsTable;
import serialCommunication.Command;
import serialCommunication.SerialCommException;
import serialCommunication.SerialCommunication;

import java.util.ArrayList;

public class SetValue extends Value {
    private int value;
    private String setRef;
    public SetValue(int value, String setRef) {
        super(value);
        this.setRef = setRef;
        this.value = value > 0 ? 1 : 0;
    }

    @Override
    public Value clone() {
        return new SetValue(0,"");
    }

    @Override
    public Object getValue() throws SemanticException, SerialCommException {
        value = getValueUpdated();
        return value;
    }

    private int getValueUpdated() throws SemanticException, SerialCommException {
        SetSymbol ss = (SetSymbol) SymbolsTable.getInstance().getVariable(setRef);
        ArrayList<IdNode> pinList = ss.getPinList();
        int val = 0;
        for(int i= 0; i <pinList.size();i++){
            PinValue pv = (PinValue) SymbolsTable.getInstance()
                    .getVariableValue(pinList.get(i).getName(),pinList.get(i).getLocation());
            if((int)pv.getValue() > 0){
                val = 1;
                break;
            }
        }
        return val;
    }

    public void updateValue() throws SemanticException, SerialCommException {
        value = getValueUpdated();
    }

    @Override
    public void setValue(Object value) throws SemanticException, SerialCommException {
        this.value = (int)value > 0 ? 1 : 0;
        SetSymbol ss = (SetSymbol) SymbolsTable.getInstance().getVariable(setRef);
        ArrayList<IdNode> pinList = ss.getPinList();
        for (IdNode item: pinList) {
            PinValue pv = (PinValue) SymbolsTable.getInstance().getVariableValue(item.getName(), item.getLocation());
            pv.setValue(this.value);
            SymbolsTable.getInstance().setVariableValue(item.getName(), pv);
            if(ParserUtils.getInstance().getParserSettings().isAvailableSerialCommunication()){
                int pinNumber = SymbolsTable.getInstance().getPinNumber(item.getName());
                Command command = this.value == 0 ? Command.SET_VALUE_LOW : Command.SET_VALUE_HIGH;
                SerialCommunication.getInstance().setValue(command,pinNumber);
            }
        }
    }

    @Override
    public String toString() {
        return value == 0 ? "LOW" : "HIGH";
    }
}
