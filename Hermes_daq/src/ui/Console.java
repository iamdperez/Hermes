package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class Console extends WindowAdapter implements WindowListener, Runnable {
    private TextArea textArea;
    private Thread firstReader;
    private Thread secondReader;
    private boolean quit;

    private final PipedInputStream firstPin = new PipedInputStream();
    private final PipedInputStream secondPin = new PipedInputStream();

    public Console(HBox hb, TextArea ta) {
        textArea = ta;
        textArea.setEditable(false);
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(100);
        textArea.setWrapText(true);

        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);
        hb.getChildren().addAll(textArea);
        HBox.setHgrow(textArea, Priority.ALWAYS);

        try {
            PipedOutputStream pout = new PipedOutputStream(this.firstPin);
            System.setOut(new PrintStream(pout, true));
        } catch (java.io.IOException io) {
            textArea.appendText("Couldn't redirect STDOUT to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.appendText("Couldn't redirect STDOUT to this console\n" + se.getMessage());
        }

        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.secondPin);
            System.setErr(new PrintStream(pout2, true));
        } catch (java.io.IOException io) {
            textArea.appendText("Couldn't redirect STDERR to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.appendText("Couldn't redirect STDERR to this console\n" + se.getMessage());
        }

        quit = false;

        firstReader = new Thread(this);
        firstReader.setDaemon(true);
        firstReader.start();

        secondReader = new Thread(this);
        secondReader.setDaemon(true);
        secondReader.start();
    }

    @Override
    public synchronized void run() {
        try {
            listenThread(firstReader,firstPin);
            listenThread(secondReader, secondPin);
        } catch (Exception e) {
            textArea.appendText("\nConsole reports an Internal error.");
            textArea.appendText("The error is: " + e);
        }
    }

    private void listenThread(Thread reader, PipedInputStream pin) throws IOException {
        while (Thread.currentThread() == reader) {
            try {
                this.wait(100);
            } catch (InterruptedException ie) {
            }
            if (pin.available() != 0) {
                String input = this.readLine(pin);
                textArea.appendText(input);
            }
            if (quit) return;
        }
    }

    public synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) break;
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        quit = true;
        this.notifyAll();
        try {
            firstReader.join(1000);
            firstPin.close();
        } catch (Exception ex) {
        }
        try {
            secondReader.join(1000);
            secondPin.close();
        } catch (Exception ex) {
        }
        System.exit(0);
    }

    public void clear() {
        textArea.clear();
    }
}

