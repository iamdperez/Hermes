package ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ToggleSwitch extends VBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    private Group switchOn;
    private Group switchOff;

    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }

    private void init() throws IOException {
        switchOn = UiUtils.getInstance().getSwitchOn();
        switchOff = UiUtils.getInstance().getSwitchOff();
        label.setText("Switch");

        getChildren().addAll(label, button);
//        getChildren().addAll( button);
        button.setOnAction((e) -> switchedOn.set(!switchedOn.get()));
        button.setGraphic(switchOff);
        button.setMaxSize(30, 20);
        button.setMinSize(30, 20);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        label.setOnMouseClicked((e) -> switchedOn.set(!switchedOn.get()));
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        setWidth(80);
        label.setAlignment(Pos.CENTER);
//        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }



    public ToggleSwitch() throws IOException {
//        button = new Button();//UiUtils.getInstance().getSwitchOn();
        init();
        switchedOn.addListener((a,b,c) -> {
            if (c) {
                button.setGraphic(switchOn);
//                label.setText("ON");
//                setStyle("-fx-background-color: green;");
//                label.toFront();
            }
            else {
                button.setGraphic(switchOff);
//                label.setText("OFF");
//                setStyle("-fx-background-color: grey;");
//                button.toFront();
            }
        });
    }
}