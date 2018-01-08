package ui;

import javafx.scene.layout.VBox;

public interface ElectronicElement {
    String getName();
    void setName(String name);
    VBox drawElement();
    void onClick();
    void onValueChanged();
}
