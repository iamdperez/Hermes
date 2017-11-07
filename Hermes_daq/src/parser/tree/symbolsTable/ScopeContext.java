package parser.tree.symbolsTable;

import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.Value;

import java.util.HashMap;
import java.util.Map;

public class ScopeContext {
    private final Map<String, Value> values;
    private final Map<String, Symbol> variables;

    public ScopeContext(){
        values = new HashMap<>();
        variables = new HashMap<>();
    }

    public void declare(String variableName, Symbol symbol){
        variables.put(variableName, symbol);
        values.put(variableName,symbol.getDefaultValue());
    }

    public Type getType(String variableName){
        return variables.get(variableName).getType();
    }

    public boolean exist(String variableName){
        return variables.containsKey(variableName);
    }

    public void setValue(String variableName, Value value){
        values.replace(variableName, value);
    }

    public Value getValue(String variableName){
        return values.get(variableName);
    }

    public Symbol getVariable(String variableName){
        return variables.get(variableName);
    }

    public void setPinIO(String variableName, IO pinIO) {
        PinSymbol ps = (PinSymbol)getVariable(variableName);
        ps.setPinIO(pinIO);
        variables.replace(variableName, ps);
    }
}
