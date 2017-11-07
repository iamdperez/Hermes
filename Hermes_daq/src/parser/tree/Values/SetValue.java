package parser.tree.values;

public class SetValue extends Value {
    private int value;
    public SetValue(int value) {
        super(value);
        this.value = value > 0 ? 1 : 0;
    }

    public SetValue(){
        super(0);
    }

    @Override
    public Value clone() {
        return new SetValue(0);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (int)value > 0 ? 1 : 0;
    }
}
