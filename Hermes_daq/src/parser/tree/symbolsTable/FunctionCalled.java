package parser.tree.symbolsTable;

public class FunctionCalled {
    public String functionName;
    public boolean called;
    public FunctionCalled(String functionName){
        this.functionName = functionName;
        called = true;
    }
}
