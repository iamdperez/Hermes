package ui.ElectronicElement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import jfxtras.labs.util.event.MouseControlUtil;
import ui.UiUtils;

import java.io.IOException;

public class ToggleButton  extends  ElectronicElement{
    private VBox vbox;
    private Group buttonRest;
    private Group buttonPress;
    public ToggleButton(String name) throws IOException {
        super(name);
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
    }

    private void setStyle() {
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10,0,0,0));
        button.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vBoxElectronic");
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
