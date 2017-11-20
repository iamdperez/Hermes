package parser.tree.values;

public class IntValue extends Value {
    private int value;
    public IntValue(int value) {
        super(value);
        this.value = value;
    }

    public IntValue(){
        super(0);
        value = 0;
    }

    @Override
    public Value clone() {
        return new IntValue((Integer) this.getValue());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (int)value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
