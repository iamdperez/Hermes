package ui.ElectronicElement;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import parser.tree.statements.ProgramNode;

public abstract class ElectronicElement {
    private String name;
    protected Label label;
    protected Button button;
    protected boolean value;
    protected ProgramNode ast;
    protected int height;
    protected int width;

    public ElectronicElement(String name){
        this.name = name;
        label = new Label();
        label.setText(name);
        button = new Button();
        height = 30;
        width = 30;
    }
    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean  getValue(){
        return value;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        label.setText(name);
    }

    public void setAst(ProgramNode ast){
        this.ast = ast;
    }
    public abstract VBox drawElement();
    public abstract void onClick();
    public abstract void onValueChanged();


}
