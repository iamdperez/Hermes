package parser.tree.values;

public abstract class Value {
    public Value(Object value) {
    }

    public abstract Value clone();
    public abstract Object getValue();
    public abstract void setValue(Object value);
}
