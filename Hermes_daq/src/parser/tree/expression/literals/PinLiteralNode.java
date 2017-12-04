package parser.tree.expression.literals;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.PinValue;
import parser.tree.values.Value;

public class PinLiteralNode extends LiteralNode {
    public PinLiteralNode(Location location, Object value) {
        super(location, value);
    }

    @Override
    public Type getType() {
        return ParserUtils.pinType;
    }

    @Override
    public Value interpret() throws SemanticException {
        PinStatus ps = (PinStatus)getValue();
        return new PinValue(ps == PinStatus.HIGH ? 1 : 0, ps == PinStatus.HIGH ? "HIGH" : "LOW" );
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return ParserUtils.pinType;
    }
}
