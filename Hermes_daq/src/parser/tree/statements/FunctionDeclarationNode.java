package parser.tree.statements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.interfaces.FunctionDeclaration;
import parser.tree.symbolsTable.FunctionSymbol;
import parser.tree.symbolsTable.OverloadedFunction;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.symbolsTable.VarSymbol;
import serialCommunication.SerialCommException;

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

    public FunctionDeclarationNode(Location location, String name,VariableDeclarationNode variables,
                                   ArrayList<StatementNode> statementList) {
        super(location);
        this.name = name;
        this.params = null;
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
    public void validateSemantic() throws SemanticException, SerialCommException {
        if(params != null){
            for(IdNode item: params){
                SymbolsTable.getInstance().declareVariable(item.getName(),new VarSymbol());
            }
        }

        if(getVariables() != null){
            variables.validateSemantic();
        }
        if(getStatementList() != null){
            for(StatementNode item: statementList){
                item.validateSemantic();
            }
        }
    }

    @Override
    public void interpret() throws SemanticException, SerialCommException {
        if(variables != null)
            variables.interpret();
        for(StatementNode item: statementList){
            item.interpret();
        }
    }

    @Override
    public String getFunctionName() {
        return name;
    }

    public void firstPassDeclaration() throws SemanticException{
        OverloadedFunction of = new OverloadedFunction(params, statementList);

        if(SymbolsTable.getInstance().variableExist(name)
                && SymbolsTable.getInstance().existOverloadedFunction(name,of)){
            throw new SemanticException(ParserUtils.getInstance().getLineErrorMessage(
                    "Function `"+name+"` was declared before. ",getLocation()));
        }

        if(!SymbolsTable.getInstance().variableExist(name)
                && !SymbolsTable.getInstance().existOverloadedFunction(name,of)){
            FunctionSymbol fs = new FunctionSymbol();
            fs.getOverloadedFunctions().add(of);
            SymbolsTable.getInstance().declareVariable(name, fs);
        }else{
            SymbolsTable.getInstance().addOverloadToFunction(name,of);
        }
    }
}
