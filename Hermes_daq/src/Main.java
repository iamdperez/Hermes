import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import serialCommunication.Commands;
import serialCommunication.SerialCommunication;

import java.io.BufferedReader;
import java.io.FileReader;
import parser.*;
public class Main {
    public static void main(String[] args){
//        SerialCommunication sc = new SerialCommunication();
//        try {
//            System.out.println("Hello world: " + sc.getArduinoModel());
//            sc.pinMode(Commands.PIN_MODE_OUTPUT, 7);
//            sc.setValue(Commands.SET_VALUE_HIGH, 7);
//            Thread.sleep(2000);
//            sc.setValue(Commands.SET_VALUE_LOW, 7);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /* Start the parser */
        try {
            ComplexSymbolFactory csf = new ComplexSymbolFactory();
            // create a buffering scanner wrapper
            ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader("src/testGrammar.txt")),csf));
            // start parsing
            parser p = new parser(lexer,csf);
            Object e = p.parse().value;
            int a = 0;
            System.out.print("Funciona prrin");
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    System.out.println("Oa");
    }
}
