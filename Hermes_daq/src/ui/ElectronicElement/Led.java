package ui.ElectronicElement;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import ui.UiUtils;

import java.io.IOException;

public class Led extends ElectronicElement {
    private Group on;
    private Group off;
    private VBox vbox;
    public Led(String name) throws IOException {
        super(name);
        vbox = new VBox(10);
        init();
    }

    private void init() throws IOException {
        on = UiUtils.getInstance().getLedOn();
        off = UiUtils.getInstance().getLedOff();

        vbox.getChildren().addAll(label, button);
        button.setGraphic(off);
        button.setMaxSize(width, height);
        button.setMinSize(width, height);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle();

        MouseControlUtil.makeDraggable(vbox);
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vBoxElectronic");
    }

    @Override
    public void setValue(boolean value){
        this.value = value;
        if(value)
            button.setGraphic(on);
        else
            button.setGraphic(off);
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
