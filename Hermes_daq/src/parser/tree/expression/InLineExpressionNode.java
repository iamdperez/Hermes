package parser.tree.expression;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

import java.util.ArrayList;

public class InLineExpressionNode extends ExpressionNode {
    public ArrayList<ExpressionNode> arguments;
    public InLineExpressionNode(Location location, ExpressionNode left, ExpressionNode right ) {
        super(location);
        arguments = new ArrayList<>();
        if(right instanceof InLineExpressionNode){
            arguments.addAll(((InLineExpressionNode) right).arguments);
        }else{
            arguments.add(right);
        }
        if(left instanceof InLineExpressionNode){
            arguments.addAll(((InLineExpressionNode)left).arguments);
        }else {
            arguments.add(0,left);
        }
    }

    @Override
    public Value interpret() throws SemanticException {
        for(ExpressionNode item: arguments){
            item.interpret();
        }
        return new IntValue();
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        for(ExpressionNode item: arguments){
            item.evaluateSemantic();
        }
        return arguments.get(0).evaluateSemantic();
    }
}
