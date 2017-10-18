package parser.tree.statements;

import parser.tree.Location;
import parser.tree.interfaces.FunctionDeclaration;

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
}
