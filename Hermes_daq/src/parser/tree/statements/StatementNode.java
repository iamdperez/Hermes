package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import serialCommunication.SerialCommException;

import java.io.IOException;

public abstract class StatementNode {
    private final Location location;

    public StatementNode(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract void validateSemantic() throws SemanticException, SerialCommException;
    public abstract void interpret() throws SemanticException;

    protected String getLineErrorMessage(String msg){
        return msg + " Line: "+
                getLocation().getLine() + " column: "+getLocation().getColumn();
    }
}
