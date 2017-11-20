package parser.tree.values;

public class StringValue extends Value {
    private String value;
    public StringValue(String value) {
        super(value);
        this.value = value;
    }

    @Override
    public Value clone() {
        return new StringValue((String) this.getValue());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String)value;
    }

    @Override
    public String toString() {
        return value;
    }
}
