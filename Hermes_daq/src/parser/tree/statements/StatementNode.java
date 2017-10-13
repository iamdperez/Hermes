package parser.tree.statements;

import parser.tree.Location;

public abstract class StatementNode {
    private final Location location;

    public StatementNode(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract void validateSemantic();
    public abstract void interpret();
}
