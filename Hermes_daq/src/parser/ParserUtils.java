package parser;

import parser.tree.Location;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.*;
import parser.tree.values.IntValue;

import java.util.HashMap;
import java.util.Map;

public final class ParserUtils {
    public final static PinType pinType = new PinType();
    public final static IntType intType = new IntType();
    public final static StringType stringType = new StringType();
    private static ParserUtils instance;
    public final static SetType setType = new SetType();
    public final static IntValue intValue = new IntValue();

    public static synchronized ParserUtils getInstance(){
        if (instance == null) {
            instance =  new ParserUtils();
            return instance;
        }
        return instance;
    }
    private ParserUtils(){
    }

    public String getLineErrorMessage(String msg, Location location) {
        return msg + " Line: "+
                location.getLine() + " column: "+location.getColumn();
    }
}
