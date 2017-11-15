package parser.tree.interfaces;

import parser.exeptions.SemanticException;

public interface FunctionDeclaration {
    void validateSemantic() throws SemanticException;
    void interpret() throws SemanticException;
}
