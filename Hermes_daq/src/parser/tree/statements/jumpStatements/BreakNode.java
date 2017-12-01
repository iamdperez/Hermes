package parser.tree.statements.jumpStatements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.symbolsTable.SymbolsTable;

public class BreakNode extends JumpStatementNode {
    public BreakNode(Location location) {
        super(location);
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(SymbolsTable.getInstance().loopingSize() <=0) {
            throw new SemanticException(
                    ParserUtils.getInstance()
                            .getLineErrorMessage("Break statement must be inside a loop statement", getLocation())
            );
        }
    }

    @Override
    public void interpret() {
        SymbolsTable.getInstance().stopLooping();
    }
}
