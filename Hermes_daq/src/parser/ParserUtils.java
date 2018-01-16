package parser;

import parser.parserSettings.ParserSettings;
import parser.tree.Location;
import parser.tree.types.*;
import parser.tree.values.IntValue;

import java.util.function.BiFunction;

public final class ParserUtils {
    public final static PinType pinType = new PinType();
    public final static IntType intType = new IntType();
    public final static StringType stringType = new StringType();
    private static ParserUtils instance;
    public final static SetType setType = new SetType();
    public final static IntValue intValue = new IntValue();
    private ParserSettings parserSettings;
    private BiFunction<String, Boolean, Boolean> onValueEvent;

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

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
    }

    public ParserSettings getParserSettings() {
        return parserSettings;
    }

    public void executeValueEvent(String uiElement, boolean value) {
        //TODO: Move this method to another place...
        onValueEvent.apply(uiElement,value);
    }

    public void setOnValueEvent(BiFunction<String, Boolean, Boolean> function){
        this.onValueEvent = function;
    }
}
