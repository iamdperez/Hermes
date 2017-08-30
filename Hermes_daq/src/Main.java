import serialCommunication.Commands;
import serialCommunication.SerialCommunication;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        SerialCommunication sc = new SerialCommunication();
        try {
            System.out.println("Hello world: " + sc.getArduinoModel());
            sc.pinMode(Commands.PIN_MODE_OUTPUT, 7);
            sc.setValue(Commands.SET_VALUE_HIGH, 7);
            Thread.sleep(2000);
            sc.setValue(Commands.SET_VALUE_LOW, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
