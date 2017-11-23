package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.interfaces.Symbol;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

import java.util.ArrayList;

public class FunctionSymbol implements Symbol {
    private IntValue value;
    private ArrayList<OverloadedFunction> overloadedFunctions;

    public FunctionSymbol(){
        overloadedFunctions = new ArrayList<>();
    }
    @Override
    public Type getType() {
        return ParserUtils.intType;
    }

    @Override
    public Value getDefaultValue() {
        value = new IntValue();
        return value;
    }

    @Override
    public Value getValue() throws SemanticException {
        return value;
    }

    @Override
    public void setValue(Value value) {
        this.value = (IntValue) value;
    }

    public ArrayList<OverloadedFunction> getOverloadedFunctions() {
        return overloadedFunctions;
    }
}
