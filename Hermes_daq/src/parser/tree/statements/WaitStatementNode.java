package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import serialCommunication.SerialCommException;

public class WaitStatementNode extends StatementNode {
    private int time;
    public WaitStatementNode(Location location, int milliSec) {
        super(location);
        time = milliSec;
    }

    @Override
    public void validateSemantic() throws SemanticException, SerialCommException {

    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
