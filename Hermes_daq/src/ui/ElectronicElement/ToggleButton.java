package ui.ElectronicElement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import ui.UiUtils;

import java.io.IOException;
import java.util.function.Function;

public class ToggleButton  extends  ElectronicElement{
    private VBox vbox;
    private Group buttonRest;
    private Group buttonPress;
    public ToggleButton(String name, Function<VBox, Boolean> deleteFunction) throws IOException {
        super(name, deleteFunction);
        vbox = new VBox(3);
        init();
    }

    private void init() throws IOException {
        buttonRest = UiUtils.getInstance().getRestButton();
        buttonPress = UiUtils.getInstance().getPressedButton();
        vbox.getChildren().addAll(button, label);
        button.setMaxSize(width, height);
        button.setMinSize(width, height);
        button.setGraphic(buttonRest);
        setStyle();
        MouseControlUtil.makeDraggable(vbox);
        button.setOnMousePressed((e) -> button.setGraphic(buttonPress));

        button.setOnMouseReleased((e) -> button.setGraphic(buttonRest));

        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction( e -> deleteFunction.apply(vbox));
        contextMenu.getItems().addAll(item1);
        vbox.setOnContextMenuRequested( e -> contextMenu.show(button,e.getScreenX(),e.getScreenY()));
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10,0,0,0));
        button.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vBoxElectronic");
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

    public void finalize(){
        button = null;
        label = null;
        buttonPress = null;
        buttonRest = null;
    }
}
