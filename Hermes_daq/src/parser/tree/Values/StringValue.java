package parser.tree.Values;

public class StringValue extends Value {
    public StringValue(String value) {
        super(value);
    }

    @Override
    public Value clone() {
        return new StringValue((String) this.getValue());
    }
}
