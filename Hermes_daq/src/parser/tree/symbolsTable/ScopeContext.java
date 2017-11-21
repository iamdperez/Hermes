package parser.tree.symbolsTable;

import parser.exeptions.SemanticException;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.Value;

import java.util.HashMap;
import java.util.Map;

public class ScopeContext {
    private final Map<String, Symbol> variables;

    public ScopeContext(){
        variables = new HashMap<>();
    }

    public void declare(String variableName, Symbol symbol){
        symbol.setValue(symbol.getDefaultValue());
        variables.put(variableName, symbol);
    }

    public Type getType(String variableName){
        return variables.get(variableName).getType();
    }

    public boolean exist(String variableName){
        return variables.containsKey(variableName);
    }

    public void setValue(String variableName, Value value){
        Symbol s = getVariable(variableName);
        s.setValue(value);
    }

    public Value getValue(String variableName) throws SemanticException {
        Symbol s = getVariable(variableName);
        return s.getValue();
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
