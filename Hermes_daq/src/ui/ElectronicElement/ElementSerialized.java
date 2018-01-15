package ui.ElectronicElement;

public class ElementSerialized {
    private String name;
    private double x;
    private double y;
    private String type;

    public ElementSerialized(String name, double x, double y, String type){
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
