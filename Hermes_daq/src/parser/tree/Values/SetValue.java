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
        
        return value;
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
