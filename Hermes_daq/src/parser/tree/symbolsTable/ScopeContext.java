package parser.tree.symbolsTable;

import parser.exeptions.SemanticException;
import parser.tree.interfaces.Symbol;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.Value;
import serialCommunication.SerialCommException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public Value getValue(String variableName) throws SemanticException, SerialCommException {
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

    public void addOverloadToFunction(String functionName, OverloadedFunction overloaded){
        FunctionSymbol fs = (FunctionSymbol)getVariable(functionName);
        fs.getOverloadedFunctions().add(overloaded);
        variables.replace(functionName,fs);
    }

    public boolean existOverloadedFunction(String functionName, OverloadedFunction overloaded){
        if(!variables.containsKey(functionName))
            return false;
        FunctionSymbol fs = (FunctionSymbol)getVariable(functionName);
        List<OverloadedFunction> of;
            of = fs.getOverloadedFunctions().stream()
                    .filter(o -> o.getParams().size() == overloaded.getParams().size())
                    .collect(Collectors.toList());
        return of.size() > 0;
    }

    public int getPinNumber(String variableName){
        PinSymbol ps = (PinSymbol)getVariable(variableName);
        return ps.getPinNumber();
    }
}
