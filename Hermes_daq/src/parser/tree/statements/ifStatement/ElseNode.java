package parser.tree.statements.ifStatement;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.statements.StatementNode;
import serialCommunication.SerialCommException;

import java.util.ArrayList;

public class ElseNode extends StatementNode {
    private final ArrayList<StatementNode> statementList;

    public ElseNode(Location location, ArrayList<StatementNode> statementList){
        super(location);
        this.statementList = statementList;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }

    @Override
    public void validateSemantic() throws SemanticException, SerialCommException {
        if(getStatementList()!=null)
            for (StatementNode item: statementList ) {
                item.validateSemantic();
            }
    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
        for (StatementNode item: statementList ) {
            item.interpret();
        }
    }
}
