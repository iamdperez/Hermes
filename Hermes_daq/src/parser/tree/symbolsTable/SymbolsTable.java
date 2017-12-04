package parser.tree.symbolsTable;

import parser.ParserUtils;
import parser.parserSettings.DeviceInfo;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.interfaces.Symbol;
import parser.tree.statements.globalVariables.IO;
import parser.tree.types.Type;
import parser.tree.values.Value;
import serialCommunication.SerialCommException;

import java.util.ArrayList;

public class SymbolsTable {
    private static SymbolsTable instance;
    private final ArrayList<ScopeContext> contexts;
    private final ArrayList<Looping> looping;
    private final ArrayList<FunctionCalled> functionCalled;
    private static DeviceInfo deviceInfo;
    private SymbolsTable(){
        contexts = new ArrayList<>();
        looping = new ArrayList<>();
        functionCalled = new ArrayList<>();
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

    public Value getVariableValue(String variableName, Location location) throws SemanticException, SerialCommException {
        for(int i = contexts.size() - 1; i >= 0; i--){
            if(contexts.get(i).exist(variableName))
                return contexts.get(i).getValue(variableName);
        }
        throw new SemanticException(
                ParserUtils.getInstance().getLineErrorMessage("Not found variable `"+variableName+"`", location)
        );
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

    public void pushLooping(Looping looping){
        this.looping.add(looping);
    }

    public Looping popLooping(){
        if(looping.size()==0)
            return null;
        Looping fl = looping.get(looping.size()-1);
        looping.remove(looping.size() - 1);
        return fl;
    }

    public int loopingSize(){
        return looping.size();
    }

    public void stopLooping(){
        Looping fl = looping.get(looping.size()-1);
        fl.looping = false;
    }

    public void addOverloadToFunction(String functionName, OverloadedFunction overloaded){
        contexts.get(0).addOverloadToFunction(functionName,overloaded);
    }

    public boolean existOverloadedFunction(String functionName, OverloadedFunction overloaded){
        return contexts.get(0).existOverloadedFunction(functionName, overloaded);
    }

    public void stopAllLoops(){
        /*for(int i = looping.size() -1; i >= 0; i--){
            looping.get(i).looping = false;
        }*/
        looping.forEach( o -> o.looping = false);
    }

    public void pushFunctionCalled(FunctionCalled functionCalled){
        this.functionCalled.add(functionCalled);
    }

    public FunctionCalled popFunctionCalled(){
        if(functionCalled.size()==0)
            return null;
        FunctionCalled fl = functionCalled.get(functionCalled.size()-1);
        functionCalled.remove(functionCalled.size() - 1);
        return fl;
    }

    public FunctionCalled getCurrentFunctionCalled(){
        if(functionCalled.size()==0)
            return null;
        return functionCalled.get(functionCalled.size()-1);
    }

    public void setDeviceInfo(DeviceInfo d){
        deviceInfo = d;
    }

    public DeviceInfo getDeviceInfo(){
        return deviceInfo;
    }

    public int getPinNumber(String variableName) throws SemanticException {
        PinSymbol ps = (PinSymbol) getVariable(variableName);
        return ps.getPinNumber();
    }
}
