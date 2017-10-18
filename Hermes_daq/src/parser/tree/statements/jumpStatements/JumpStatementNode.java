package parser.tree.statements.jumpStatements;

import parser.tree.Location;
import parser.tree.statements.StatementNode;

public abstract class JumpStatementNode extends StatementNode {
    public JumpStatementNode(Location location) {
        super(location);
    }
}
