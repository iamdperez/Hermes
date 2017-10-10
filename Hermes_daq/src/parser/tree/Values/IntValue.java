package parser.tree.Values;

public class IntValue extends Value {

    public IntValue(Integer value) {
        super(value);
    }

    @Override
    public Value clone() {
        return new IntValue((Integer) this.getValue());
    }
}
