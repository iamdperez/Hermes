package parser.tree.interfaces;

import parser.exeptions.SemanticException;
import serialCommunication.SerialCommException;

import java.io.IOException;

public interface FunctionDeclaration {
    void validateSemantic() throws SemanticException, SerialCommException;
    void interpret() throws SemanticException;
}
