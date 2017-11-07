package parser.tree.values;

public class SetValue extends Value {
    public SetValue(int value) {
        super(value);
    }

    public SetValue(){
        super(0);
    }

    @Override
    public Value clone() {
        return new SetValue(0);
    }
}
