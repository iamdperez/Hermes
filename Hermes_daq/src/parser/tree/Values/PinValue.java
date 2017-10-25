package parser.tree.values;

public class PinValue extends Value {
    public PinValue(Object value) {
        super(value);
    }

    public PinValue(){
        super(0);
    }
    @Override
    public Value clone() {
        return new PinValue((Integer) this.getValue());
    }
}
