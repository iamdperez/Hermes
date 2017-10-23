package parser.tree.Values;

public class IntValue extends Value {

    public IntValue(Integer value) {
        super(value);
    }

    public IntValue(){
        super(0);
    }

    @Override
    public Value clone() {
        return new IntValue((Integer) this.getValue());
    }
}
