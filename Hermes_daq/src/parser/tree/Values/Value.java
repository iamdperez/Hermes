package parser.tree.Values;

public abstract class Value {
    public abstract Value clone();
    public abstract void setValue(Object value);
    public abstract Object getValue();
}
