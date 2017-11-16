package parser.tree.statements;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.interfaces.FunctionDeclaration;

import java.util.ArrayList;

public class MainNode extends StatementNode implements FunctionDeclaration {
    private final VariableDeclarationNode variables;

    public VariableDeclarationNode getVariables() {
        return variables;
    }

    public ArrayList<StatementNode> getStatementList() {
        return statementList;
    }

    private final ArrayList<StatementNode> statementList;

    public MainNode(Location location, VariableDeclarationNode variables,
                    ArrayList<StatementNode> statementList) {
        super(location);
        this.variables = variables;
        this.statementList = statementList;
    }

    @Override
    public void validateSemantic() throws SemanticException {
        if(variables != null)
            variables.validateSemantic();
        for(StatementNode item: statementList){
            item.validateSemantic();
        }
    }

    @Override
    public void interpret() throws SemanticException {
        for(StatementNode item: statementList){
            item.interpret();
        }
    }
}
