package parser.tree.values;

public class PinValue extends Value {
    private int value;
    public PinValue(int value) {
        super(value);
        this.value = value > 0 ? 1 : 0;
    }

    public PinValue(){
        super(0);
    }
    @Override
    public Value clone() {
        return new PinValue((int)this.getValue());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (int)value > 0 ? 1 : 0;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
