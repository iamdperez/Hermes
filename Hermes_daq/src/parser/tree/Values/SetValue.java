package parser.tree.Values;

import java.util.ArrayList;

public class SetValue extends Value {
    public SetValue(ArrayList<String> value) {
        super(value);
    }

    public SetValue(){
        super(new ArrayList<String>());

    }

    @Override
    public Value clone() {
        return new SetValue((ArrayList<String>) getValue());
    }
}
