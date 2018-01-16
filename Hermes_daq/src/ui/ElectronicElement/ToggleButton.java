package ui.ElectronicElement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import parser.exeptions.SemanticException;
import parser.tree.interfaces.FunctionDeclaration;
import serialCommunication.SerialCommException;
import ui.UiUtils;

import java.io.IOException;
import java.util.function.Function;

public class ToggleButton  extends  ElectronicElement{
    private VBox vbox;
    private Group buttonRest;
    private Group buttonPress;
    private FunctionDeclaration onClickFunction;

    public ToggleButton(String name, Function<String, Boolean> deleteFunction) throws IOException {
        super(name, deleteFunction);
        vbox = new VBox(3);
        init();
    }

    public ToggleButton(String name, Function<String,Boolean> deleteFunction, double x, double y) throws IOException {
        super(name, deleteFunction);
        vbox = new VBox(3);
        init();
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
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
        button.setOnMousePressed((e) -> {
            button.setGraphic(buttonPress);
            value = true;
        });
        button.setOnMouseClicked(e -> onClick());
        button.setOnMouseReleased((e) -> {
            button.setGraphic(buttonRest);
            value = false;
        });

        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction( e -> deleteFunction.apply(getName()));
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
        try {
            if(!UiUtils.getInstance().isRunning())
                return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                onClickFunction.interpret();
            } catch (SemanticException | SerialCommException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onValueChanged() {

    }

    @Override
    public String getType() {
        return "Button";
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
        String v = "\nfunction "+name+"_onClick()\n\nendfunction\n\n";
        return v;
    }
    public void finalize(){
        button = null;
        label = null;
        buttonPress = null;
        buttonRest = null;
    }

    public void setOnClickFunction(FunctionDeclaration onClickFunction) {
        this.onClickFunction = onClickFunction;
    }
}
