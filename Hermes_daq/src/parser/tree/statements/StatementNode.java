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
    public abstract void interpret();

    protected String getLineErrorMessage(String msg, Location location) {
        return msg + " Line: "+
                location.getLine() + " column: "+location.getColumn();
    }

    protected String getLineErrorMessage(String msg){
        return msg + " Line: "+
                getLocation().getLine() + " column: "+getLocation().getColumn();
    }
}
