package parser.tree.statements.globalVariables;

import parser.tree.Location;
import parser.tree.statements.StatementNode;

public abstract class GlobalVariablesNode extends StatementNode {
    public GlobalVariablesNode(Location location) {
        super(location);
    }
}
