import java_cup.runtime.ComplexSymbolFactory;
import parser.Lexer;
import parser.parser;
import parser.parserSettings.ParserSettings;
import parser.tree.statements.ProgramNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ParserCode {
    private parser parser;
    public ParserCode(String sourcePath, ParserSettings parserSettings) throws FileNotFoundException {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Lexer lexer = new  Lexer(new BufferedReader(new FileReader(sourcePath)),csf);
        parser = new parser(lexer,csf, parserSettings);
    }

    public ProgramNode getAST() throws Exception {
        ProgramNode pn = (ProgramNode)parser.parse().value;
        pn.validateSemantic();
        return pn;
    }
}
