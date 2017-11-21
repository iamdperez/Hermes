package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.expression.IdNode;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.SetValue;
import parser.tree.values.Value;

import java.util.ArrayList;

public class SetSymbol implements Symbol{
    private final ArrayList<IdNode> pinList;
    private final String setRef;
    private SetValue value;
    public SetSymbol(ArrayList<IdNode> idList, String setRef) {
        pinList = idList;
        this.setRef = setRef;
    }

    @Override
    public Type getType() {
        return ParserUtils.setType;
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
        value = new SetValue(v, setRef);
        return value;
    }

    @Override
    public Value getValue() throws SemanticException {
        value.updateValue();
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = (SetValue)value;
    }

    public ArrayList<IdNode> getPinList() {
        return pinList;
    }
}
