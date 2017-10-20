package parser.tree.expression;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.Types.Type;
import parser.tree.Values.Value;

public abstract class ExpressionNode {

    private final Location location;
    public ExpressionNode(Location location){
        this.location = location;
    }
    public abstract Value interpret();
    public abstract Type evaluateSemantic() throws SemanticException;
    public Location getLocation() {
        return location;
    }
    protected void validateIdNode(ExpressionNode node) throws SemanticException {
        if(node instanceof IdNode){
            if(node == null){
                IdNode id = (IdNode)node;
                throw new SemanticException("`"+id.getName()+
                        "` must be declared before line: " +
                        id.getLocation().getLine()+" column: "
                        +id.getLocation().getColumn());
            }
        }
    }
}
