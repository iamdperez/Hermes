package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.interfaces.FunctionDeclaration;
import parser.tree.symbolsTable.SymbolsTable;

import java.util.ArrayList;

public class ProgramNode {
    private final Location location;
    private final String moduleName;
    private final InitialNode initial;
    private final ArrayList<FunctionDeclaration> functionList;

    public ProgramNode(Location location, String moduleName,
                       InitialNode initial, ArrayList<FunctionDeclaration> functionList ){
        this.location = location;
        this.moduleName = moduleName;
        this.initial = initial;
        this.functionList = functionList;
    }

    public Location getLocation() {
        return location;
    }

    public String getModuleName() {
        return moduleName;
    }

    public InitialNode getInitial() {
        return initial;
    }

    public ArrayList<FunctionDeclaration> getFunctionList() {
        return functionList;
    }

    public void validateSemantic() throws SemanticException {
        SymbolsTable.getInstance().pushNewContext();
        initial.validateSemantic();
    }

    public void interpretCode(){
        initial.interpret();
    }
}
