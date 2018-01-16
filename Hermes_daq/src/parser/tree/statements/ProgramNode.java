package parser.tree.statements;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.parserSettings.ParserSettings;
import parser.tree.Location;
import parser.tree.interfaces.FunctionDeclaration;
import parser.tree.symbolsTable.SymbolsTable;
import serialCommunication.SerialCommException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramNode {
    private final Location location;
    private final String moduleName;
    private final InitialNode initial;
    private final ArrayList<FunctionDeclaration> functionList;
    private final ParserSettings parserSettings;
    public ProgramNode(Location location, String moduleName,
                       InitialNode initial, ArrayList<FunctionDeclaration> functionList,
                       ParserSettings parserSettings){
        this.location = location;
        this.moduleName = moduleName;
        this.initial = initial;
        this.functionList = functionList;
        this.parserSettings = parserSettings;
    }

    public Location getLocation() {
        return location;
    }

    public String getModuleName() {
        return moduleName;
    }

    public InitialNode getInitial() {
        return initial;
    }

    public ArrayList<FunctionDeclaration> getFunctionList() {
        return functionList;
    }

    public void validateSemantic() throws SemanticException, SerialCommException {
        ParserUtils.getInstance().setParserSettings(parserSettings);
        SymbolsTable.getInstance().pushNewContext();
        if(initial != null)
            initial.validateSemantic();
        List<FunctionDeclaration> main = functionList.stream().filter( o -> o instanceof MainNode)
                .collect(Collectors.toList());
        if(main.size() > 1) {
            throw new SemanticException("Method `main` is already defined in module `" + getModuleName() + "`");
        }

        List<FunctionDeclaration> functions = functionList.stream().filter( o -> o instanceof FunctionDeclarationNode)
                .collect(Collectors.toList());
        for (FunctionDeclaration item: functions) {
            ((FunctionDeclarationNode)item).firstPassDeclaration();
        }

        for(FunctionDeclaration item: functionList){
            SymbolsTable.getInstance().pushNewContext();
            item.validateSemantic();
            SymbolsTable.getInstance().popContext();
        }
    }

    public void interpretCode() throws SemanticException, SerialCommException {
        if(initial != null)
            initial.interpret();
//        Optional<FunctionDeclaration> main = functionList.stream().filter( o -> o instanceof MainNode).findFirst();
//        if(main.isPresent()) {
//            MainNode mainNode = (MainNode) main.get();
//            SymbolsTable.getInstance().pushNewContext();
//            mainNode.interpret();
//            SymbolsTable.getInstance().popContext();
//        }
//        SymbolsTable.getInstance().popContext();
    }
}
