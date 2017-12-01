import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java_cup.runtime.ComplexSymbolFactory;
import parser.deviceInfo.DeviceInfo;
import parser.tree.statements.ProgramNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import parser.*;
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

        /* Start the parser */
        try {
            ComplexSymbolFactory csf = new ComplexSymbolFactory();
            // create a buffering scanner wrapper
            Lexer lexer = new  Lexer(new BufferedReader(new FileReader("src/testGrammar.txt")),csf);
            // start parsing

            Gson gSon = new Gson();
            StringBuffer sb = new StringBuffer();
            Files.readAllLines(Paths.get("src/devicesInfo.json")).forEach(s -> sb.append(s));
            String jSon = sb.toString();
            Type listType = new TypeToken<ArrayList<DeviceInfo>>() {}.getType();
            ArrayList<DeviceInfo> devices = gSon.fromJson(jSon, listType);
            parser p = new parser(lexer,csf, devices);
            ProgramNode v = (ProgramNode) p.parse().value;
            v.validateSemantic();
            v.interpretCode();


            int a = 0;
           // System.out.println("Funciona prrin");
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    //System.out.println("Oa");
    }
}
