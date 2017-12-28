package ui;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Console
{
    private TextArea textArea;

    public Console(HBox hb, TextArea ta)
    {


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
        redirectSystemStreams();
    }


    public void clear() {
        textArea.clear();
    }

    private void updateTextArea(String text){
        Platform.runLater(() -> textArea.appendText(text));
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }

        };

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));
    }
}
