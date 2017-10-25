package parser.tree.symbolsTable;

import parser.tree.types.Type;
import parser.tree.values.Value;

import java.util.HashMap;
import java.util.Map;

public class ScopeContext {
    private final Map<String, Value> values;
    private final Map<String, Type> variables;

    public ScopeContext(){
        values = new HashMap<>();
        variables = new HashMap<>();
    }

    public void declare(String variableName, Type type){
        variables.put(variableName, type);
        values.put(variableName,type.getDefaultValue());
    }

    public Type getType(String variableName){
        return variables.get(variableName);
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


}
