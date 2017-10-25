package parser.tree.values;

public class StringValue extends Value {
    public StringValue(String value) {
        super(value);
    }

    public StringValue(){
        super("");
    }

    @Override
    public Value clone() {
        return new StringValue((String) this.getValue());
    }
}
