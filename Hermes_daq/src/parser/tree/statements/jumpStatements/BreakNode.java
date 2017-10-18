package parser.tree.statements.jumpStatements;

import parser.tree.Location;

public class BreakNode extends JumpStatementNode {
    public BreakNode(Location location) {
        super(location);
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }
}
