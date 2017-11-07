package parser.tree.symbolsTable;

import parser.tree.expression.IdNode;
import parser.tree.types.SetType;
import parser.tree.types.Type;
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
        return new SetValue();
    }

    public ArrayList<IdNode> getPinList() {
        return pinList;
    }
}
