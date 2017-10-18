package parser.tree.statements;

import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.interfaces.FunctionDeclaration;

import java.util.ArrayList;

public class FunctionDeclarationNode extends StatementNode implements FunctionDeclaration {
    private final String name;
    private final ArrayList<IdNode> params;
    private final VariableDeclarationNode variables;
    private final ArrayList<StatementNode> statementList;

    public FunctionDeclarationNode(Location location, String name,
                                   ArrayList<IdNode> params, VariableDeclarationNode variables,
                                   ArrayList<StatementNode> statementList) {
        super(location);
        this.name = name;
        this.params = params;
        this.variables = variables;
        this.statementList = statementList;
    }
    public String getName() {
        return name;
    }

    public ArrayList<IdNode> getParams() {
        return params;
    }

    public VariableDeclarationNode getVariables() {
        return variables;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public void interpret() {

    }
}
