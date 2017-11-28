import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import parser.tree.interfaces.DeviceInfo;
import parser.tree.statements.ProgramNode;
import parser.tree.symbolsTable.SymbolsTable;
import serialCommunication.Commands;
import serialCommunication.SerialCommunication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

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
            ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader("src/testGrammar.txt")),csf));
            // start parsing

            Gson gSon = new Gson();
            StringBuffer sb = new StringBuffer();

            List<String> ls = Files.readAllLines("src/devicesInfo.json").forEach( s -> sb.append(s));
            String jSon = "";
            Type listType = new TypeToken<ArrayList<DeviceInfo>>() {}.getType();
            ArrayList<DeviceInfo> devices = gSon.fromJson(jSon, listType);
            parser p = new parser(lexer,csf, (ArrayList<DeviceInfo>) devices);
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
