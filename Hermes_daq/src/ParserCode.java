import java_cup.runtime.ComplexSymbolFactory;
import javafx.scene.control.TextArea;
import parser.Lexer;
import parser.parser;
import parser.parserSettings.ParserSettings;
import parser.tree.statements.ProgramNode;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ParserCode {
    private final TextArea console;
    private String sourcePath;
    private  ParserSettings parserSettings;
    public ParserCode(String sourcePath, ParserSettings parserSettings, TextArea console) throws FileNotFoundException {
        this.sourcePath = sourcePath;
        this.parserSettings = parserSettings;
        this.console = console;
    }

    public ProgramNode getAST() throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Lexer lexer = new Lexer(new BufferedReader(new FileReader(sourcePath)),csf);
        parser parser = new parser(lexer,csf, parserSettings, console);
        ProgramNode pn = (ProgramNode)parser.parse().value;
        pn.validateSemantic();
        return pn;
    }
}
