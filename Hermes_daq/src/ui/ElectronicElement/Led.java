package ui.ElectronicElement;

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

public class Led extends ElectronicElement {
    private Group on;
    private Group off;
    private VBox vbox;
    public Led(String name, Function<String, Boolean> deleteFunction) throws IOException {
        super(name, deleteFunction);
        vbox = new VBox(10);
        init();
    }

    public Led(String name, Function<String,Boolean> deleteFunction, double x, double y) throws IOException {
        super(name, deleteFunction);
        vbox = new VBox(10);
        init();
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
    }

    @Override
    public double getX() {
        return vbox.getLayoutX();
    }

    @Override
    public double getY() {
        return vbox.getLayoutY();
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

        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction( e -> deleteFunction.apply(getName()));
        contextMenu.getItems().addAll(item1);
        vbox.setOnContextMenuRequested( e -> contextMenu.show(button,e.getScreenX(),e.getScreenY()));
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        button.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vBoxElectronic");
        button.getStyleClass().add("iconButton");
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

    @Override
    public String getType() {
        return "Led";
    }

    public void finalize(){
        button = null;
        label = null;
        on = null;
        off = null;
    }
}
