package parser.tree.symbolsTable;

import parser.exeptions.SemanticException;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.Value;

import java.util.ArrayList;

public class SymbolsTable {
    private static SymbolsTable instance;
    private final ArrayList<ScopeContext> contexts;
    private SymbolsTable(){
        contexts = new ArrayList<>();
    }
    public static synchronized SymbolsTable getInstance(){
        if (instance == null) {
            instance =  new SymbolsTable();
            return instance;
        }
        return instance;
    }

    public boolean variableExist(String variableName){
        for(int i = contexts.size() - 1; i >= 0; i--){
            if(contexts.get(i).exist(variableName))
                return true;
        }
        return false;
    }

    public Value getVariableValue(String variableName) throws SemanticException {
        for(int i = contexts.size() - 1; i >= 0; i--){
            if(contexts.get(i).exist(variableName))
                return contexts.get(i).getValue(variableName);
        }
        throw new SemanticException("Not found variable `"+variableName+"`");
    }

    public void pushNewContext(){
        contexts.add(new ScopeContext());
    }

    public ScopeContext popContext(){
        if(contexts.size()==0)
            return null;
        ScopeContext sc = contexts.get(contexts.size()-1);
        contexts.remove(contexts.size() - 1);
        return sc;
    }

    private ScopeContext getCurrentContext(){
        return contexts.get(contexts.size() - 1);
    }

    public void declareVariable(String variableName, Symbol symbol){
        ScopeContext sc = getCurrentContext();
        sc.declare(variableName,symbol);
    }

    public Type getVariableType(String variableName) throws SemanticException {
        for(int i = contexts.size() - 1; i >= 0; i--){
            if(contexts.get(i).exist(variableName))
                return contexts.get(i).getType(variableName);
        }
        throw new SemanticException("Not found variable `"+variableName+"`");
    }

    public void setVariableValue(String variableName, Value value){
        for (int i = contexts.size() - 1; i >= 0; i--)
        {
            if (contexts.get(i).exist(variableName)){
                contexts.get(i).setValue(variableName,value);
                break;
            }
        }
    }

    public void setPinIO(String variableName, IO pinIO) {
        ScopeContext sc = getCurrentContext();
        sc.setPinIO(variableName, pinIO);
    }

    public Symbol getVariable(String variableName) throws SemanticException {
        for(int i = contexts.size() - 1; i >= 0; i--){
            if(contexts.get(i).exist(variableName))
                return contexts.get(i).getVariable(variableName);
        }
        throw new SemanticException("Not found variable `"+variableName+"`");
    }
}
