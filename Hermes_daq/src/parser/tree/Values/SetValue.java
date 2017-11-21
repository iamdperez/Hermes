package parser.tree.values;

import parser.exeptions.SemanticException;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SetSymbol;
import parser.tree.symbolsTable.SymbolsTable;

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
    public Object getValue() throws SemanticException {
        value = getValueUpdated();
        return value;
    }

    private int getValueUpdated() throws SemanticException {
        SetSymbol ss = (SetSymbol) SymbolsTable.getInstance().getVariable(setRef);
        ArrayList<IdNode> pinList = ss.getPinList();
        int val = 0;
        for(int i= 0; i <pinList.size();i++){
            PinValue pv = (PinValue) SymbolsTable.getInstance().getVariableValue(pinList.get(i).getName());
            if((int)pv.getValue() > 0){
                val = 1;
                break;
            }
        }
        return val;
    }

    public void updateValue() throws SemanticException {
        value = getValueUpdated();
    }

    @Override
    public void setValue(Object value) throws SemanticException {
        this.value = (int)value > 0 ? 1 : 0;
        SetSymbol ss = (SetSymbol) SymbolsTable.getInstance().getVariable(setRef);
        ArrayList<IdNode> pinList = ss.getPinList();
        for (IdNode item: pinList) {
            PinValue pv = (PinValue) SymbolsTable.getInstance().getVariableValue(item.getName());
            pv.setValue(this.value);
            SymbolsTable.getInstance().setVariableValue(item.getName(), pv);
                /*TODO: set with serialCommunication value to Arduino*/
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
