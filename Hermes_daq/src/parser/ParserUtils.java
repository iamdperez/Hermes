package parser;

import parser.tree.Types.IntType;
import parser.tree.Types.PinType;
import parser.tree.Types.StringType;
import parser.tree.Types.Type;

import java.util.HashMap;
import java.util.Map;

public final class ParserUtils {
    private static Map<String,Type> map;
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
