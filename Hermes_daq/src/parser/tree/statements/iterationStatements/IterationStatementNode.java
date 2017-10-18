package parser.tree.statements.iterationStatements;

import parser.tree.Location;
import parser.tree.statements.StatementNode;

public abstract class IterationStatementNode extends StatementNode {
    public IterationStatementNode(Location location) {
        super(location);
    }
}
