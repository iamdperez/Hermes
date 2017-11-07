package parser;

import parser.tree.types.IntType;
import parser.tree.types.PinType;
import parser.tree.types.StringType;
import parser.tree.types.Type;

import java.util.HashMap;
import java.util.Map;

public final class ParserUtils {
    private static Map<String,Type> map;
    public final static String pinType = "PinType";
    public final static String intType = "IntType";
    public final static String stringType = "StringType";
    private ParserUtils(){
        map = new HashMap<>();
        map.put("PinType", new PinType());
        map.put("IntType", new IntType());
        map.put("StringType", new StringType());
    }

    public static Type getTypeNode(String type){
        return map.get(type);
    }
}
