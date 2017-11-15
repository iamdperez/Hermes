package parser.tree.symbolsTable;

import parser.exeptions.SemanticException;
import parser.tree.expression.IdNode;
import parser.tree.types.SetType;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.SetValue;
import parser.tree.values.Value;

import java.util.ArrayList;

public class SetSymbol implements Symbol{
    private final ArrayList<IdNode> pinList;

    public SetSymbol(ArrayList<IdNode> idList) {
        pinList = idList;
    }

    @Override
    public Type getType() {
        return new SetType();
    }

    @Override
    public Value getDefaultValue() {
        int v = 0;
        try{
            for(int i = 0; i < pinList.size(); i++){
                PinValue pv = (PinValue) SymbolsTable.getInstance().getVariableValue(pinList.get(i).getName());
                v = (int)pv.getValue();
                if(v != 0)
                    break;
            }
        }catch (SemanticException e){
            v = 0;
        }
        return new SetValue(v);
    }

    public ArrayList<IdNode> getPinList() {
        return pinList;
    }
}
