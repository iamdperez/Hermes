package parser.tree.values;

public abstract class Value {
    private Object value;
    public Value(Object value){
        this.value = value;
    }
    public abstract Value clone();
    public Object getValue(){
        return value;
    }
}
