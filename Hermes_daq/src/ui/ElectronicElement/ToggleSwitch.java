package ui.ElectronicElement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import ui.UiUtils;

import java.io.IOException;

public class ToggleSwitch extends ElectronicElement {
    private Group switchOn;
    private Group switchOff;
    private VBox vbox;
    private SimpleBooleanProperty switchedOn;

    public ToggleSwitch(String name) throws IOException {
        super(name);
        vbox = new VBox(1);
        switchedOn = new SimpleBooleanProperty(false);
        init();

        switchedOn.addListener((a,b,c) -> {
            if(c){
                button.setGraphic(switchOn);
            }else{
                button.setGraphic(switchOff);
            }
        });
    }

    private void init() throws IOException {
        switchOn = UiUtils.getInstance().getSwitchOn();
        switchOff = UiUtils.getInstance().getSwitchOff();

        vbox.getChildren().addAll(button,label);

        button.setOnAction( (e)-> switchedOn.set(!switchedOn.get()));
        button.setGraphic(switchOff);
        button.setMaxSize(width, height);
        button.setMinSize(width, height);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle();

        MouseControlUtil.makeDraggable(vbox);
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
    }

    @Override
    public VBox drawElement() {
        return vbox;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onValueChanged() {

    }
}
