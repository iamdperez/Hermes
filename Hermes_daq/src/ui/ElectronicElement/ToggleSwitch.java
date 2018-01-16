package ui.ElectronicElement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import ui.UiUtils;

import java.io.IOException;
import java.util.function.Function;

public class ToggleSwitch extends ElectronicElement {
    private Group switchOn;
    private Group switchOff;
    private VBox vbox;
    private SimpleBooleanProperty switchedOn;

    public ToggleSwitch(String name, Function<String, Boolean> deleteFunction) throws IOException {
        super(name, deleteFunction);
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

    public ToggleSwitch(String name, Function<String,Boolean> deleteFunction, double x, double y) throws IOException {
        super(name, deleteFunction);
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
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
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

        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction( e -> deleteFunction.apply(getName()));
        contextMenu.getItems().addAll(item1);
        vbox.setOnContextMenuRequested( e -> contextMenu.show(button,e.getScreenX(),e.getScreenY()));
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("iconButton");
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

    @Override
    public String getType() {
        return "Switch";
    }

    @Override
    public double getX() {
        return vbox.getLayoutX();
    }

    @Override
    public double getY() {
        return vbox.getLayoutY();
    }

    @Override
    public String getEventsFunctions() {
        String v = "\nfunction "+name+"_onValueChange(value)\n\nendfunction\n\n";
        return v;
    }

    public void finalize(){
        button = null;
        label = null;
        switchedOn = null;
        switchOff = null;
        switchOn = null;

    }


}
