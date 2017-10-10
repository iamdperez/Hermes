package parser.tree.expression;

import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;

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
    public Value Interpret() {
        return null;
    }

    @Override
    public Type EvaluateSemantic() {
        return null;
    }

    public IdNode getName() {
        return name;
    }

    public ArrayList<ExpressionNode> getArgumentList() {
        return argumentList;
    }
}
