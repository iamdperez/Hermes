package ui.ElectronicElement;

import javafx.scene.layout.VBox;

public abstract class ElectronicElement {
    private String name;
    private boolean value;

    public ElectronicElement(String name){
        this.name = name;
    }
    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public abstract VBox drawElement();
    public abstract void onClick();
    public abstract void onValueChanged();
}
