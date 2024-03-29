package parser.tree.symbolsTable;

import parser.tree.expression.IdNode;
import parser.tree.statements.StatementNode;

import java.util.ArrayList;

public class OverloadedFunction {
    private ArrayList<IdNode> params;
    private ArrayList<StatementNode> statements;

    public OverloadedFunction(ArrayList<IdNode> params, ArrayList<StatementNode> statements){
        this.params = params == null ? new ArrayList<>() : params;
        this.statements = statements == null ? new ArrayList<>() : statements;
    }

    public ArrayList<IdNode> getParams() {
        return params;
    }

    public ArrayList<StatementNode> getStatements() {
        return statements;
    }
}
