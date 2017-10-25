package parser.tree.expression;

import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;

import java.util.ArrayList;

public class FunctionCallNode extends ExpressionNode {
    private final IdNode name;
    private final ArrayList<ExpressionNode> argumentList;

    public FunctionCallNode(Location location, IdNode name) {
        super(location);
        this.name = name;
        argumentList = null;
    }

    public FunctionCallNode(Location location, IdNode name, ArrayList<ExpressionNode> argumentList){
        super(location);
        this.name = name;
        this.argumentList = argumentList;
    }

    @Override
    public Value interpret() {
        return null;
    }

    @Override
    public Type evaluateSemantic() {
        return null;
    }

    public IdNode getName() {
        return name;
    }

    public ArrayList<ExpressionNode> getArgumentList() {
        return argumentList;
    }
}
