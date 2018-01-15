package ui.ElectronicElement;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import parser.tree.statements.ProgramNode;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class ElectronicElement implements Serializable{
    protected String name;
    protected Label label;
    protected Button button;
    protected boolean value;
    protected ProgramNode ast;
    protected int height;
    protected int width;
    protected Function<String,Boolean> deleteFunction;

    public ElectronicElement(String name, Function<String,Boolean> deleteFunction){
        this.name = name;
        label = new Label();
        label.setText(name);
        button = new Button();
        height = 30;
        width = 30;
        this.deleteFunction = deleteFunction;
    }

    public ElectronicElement(String name, Function<String,Boolean> deleteFunction, double x, double y){
        this.name = name;
        label = new Label();
        label.setText(name);
        button = new Button();
        height = 30;
        width = 30;
        this.deleteFunction = deleteFunction;
    }

    public abstract double getX();
    public abstract double getY();
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
    public abstract String getType();
}
