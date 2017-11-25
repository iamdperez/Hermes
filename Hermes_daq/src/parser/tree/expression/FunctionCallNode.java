package parser.tree.expression;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.statements.StatementNode;
import parser.tree.symbolsTable.*;
import parser.tree.types.IntType;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCallNode extends ExpressionNode {
    private final IdNode functionName;
    private final ArrayList<ExpressionNode> argumentList;

    public FunctionCallNode(Location location, IdNode name) {
        super(location);
        this.functionName = name;
        argumentList = null;
    }

    public FunctionCallNode(Location location, IdNode name, ArrayList<ExpressionNode> argumentList){
        super(location);
        this.functionName = name;
        this.argumentList = argumentList;
    }

    @Override
    public Value interpret() throws SemanticException {
        FunctionSymbol fs = (FunctionSymbol)SymbolsTable.getInstance().getVariable(functionName.getName());
        SymbolsTable.getInstance().setVariableValue(functionName.getName(),ParserUtils.intValue);
        SymbolsTable.getInstance().pushNewContext();
        OverloadedFunction of;
        if(argumentList !=null){
            of = getOverloadedFunctions(argumentList.size()).get(0);
            of.getParams().forEach( o -> SymbolsTable.getInstance().declareVariable(o.getName(), new VarSymbol()));
            for(int i = 0; i < argumentList.size(); i++){
                ExpressionNode item = argumentList.get(i);
                IntValue paramValue = (IntValue) item.interpret();
                SymbolsTable.getInstance().setVariableValue(of.getParams().get(i).getName(),paramValue);
            }
        }else{
            of = getOverloadedFunctions(0).get(0);
        }

        FunctionCalled fc = new FunctionCalled(functionName.getName());
        SymbolsTable.getInstance().pushFunctionCalled(fc);
        for(StatementNode item: of.getStatements()){
            item.interpret();
            if(!fc.called)
                break;
        }
        SymbolsTable.getInstance().popFunctionCalled();
        SymbolsTable.getInstance().popContext();
        return fs.getValue();
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        if(!SymbolsTable.getInstance().variableExist(functionName.getName())){
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage(
                            "Function `"+ functionName.getName()+"` must be declared before. ",getLocation())
            );
        }
        if(paramsAreValid())
            return SymbolsTable.getInstance().getVariableType(functionName.getName());

        throw new SemanticException(
                ParserUtils.getInstance().getLineErrorMessage(
                        "Function `"+functionName.getName()+"` must send the correct params. ",getLocation())
        );
    }

    private boolean paramsAreValid() throws SemanticException {
        if(argumentList == null){
            List<OverloadedFunction> ofl = getOverloadedFunctions(0);
            return ofl.size() == 1;
        }

        List<OverloadedFunction> ofl = getOverloadedFunctions(argumentList.size());
        if(ofl.size() > 1)
            return false;

        for (ExpressionNode item: argumentList) {
            Type t = item.evaluateSemantic();
            if(!(t instanceof IntType)){
                throw new SemanticException(
                        ParserUtils.getInstance()
                                .getLineErrorMessage(
                                        "Param in function `"+functionName.getName()
                                                +"` must be IntType not `"+t+"`. ",item.getLocation())
                );
            }

        }
         return true;
    }

    private List<OverloadedFunction> getOverloadedFunctions(int paramsCount) throws SemanticException {
        FunctionSymbol fs = (FunctionSymbol)SymbolsTable.getInstance().getVariable(functionName.getName());
        return fs.getOverloadedFunctions().stream()
                .filter( o -> o.getParams().size() == paramsCount)
                .collect(Collectors.toList());
    }

    public IdNode getFunctionName() {
        return functionName;
    }

    public ArrayList<ExpressionNode> getArgumentList() {
        return argumentList;
    }
}
