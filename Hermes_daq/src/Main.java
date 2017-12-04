import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import parser.parserSettings.ParserSettings;
import parser.tree.statements.ProgramNode;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
    public static void main(String[] args){
//        SerialCommunication sc = new SerialCommunication();
//        try {
//            System.out.println("Hello world: " + sc.getArduinoModel());
//            sc.pinMode(Commands.PIN_MODE_OUTPUT, 7);
//            sc.setVariableValue(Commands.SET_VALUE_HIGH, 7);
//            Thread.sleep(2000);
//            sc.setVariableValue(Commands.SET_VALUE_LOW, 7);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            Gson gSon = new Gson();
            StringBuffer sb = new StringBuffer();
            Files.readAllLines(Paths.get("src/parserSettings.json")).forEach(s -> sb.append(s));
            String jSon = sb.toString();
            Type listType = new TypeToken<ParserSettings>() {}.getType();
            ParserSettings ps = gSon.fromJson(jSon, listType);
            ParserCode pc = new ParserCode("src/testGrammar.txt", ps);
            ProgramNode pn = pc.getAST();
            pn.interpretCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
