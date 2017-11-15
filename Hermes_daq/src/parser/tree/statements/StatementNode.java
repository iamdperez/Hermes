package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;

public abstract class StatementNode {
    private final Location location;

    public StatementNode(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract void validateSemantic() throws SemanticException;
    public abstract void interpret() throws SemanticException;

    protected String getLineErrorMessage(String msg){
        return msg + " Line: "+
                getLocation().getLine() + " column: "+getLocation().getColumn();
    }
}
