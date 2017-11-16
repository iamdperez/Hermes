package parser.tree.expression.literals;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;

public class PinLiteralNode extends LiteralNode {
    public PinLiteralNode(Location location, Object value) {
        super(location, value);
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Value interpret() throws SemanticException {
        return null;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return null;
    }
}
