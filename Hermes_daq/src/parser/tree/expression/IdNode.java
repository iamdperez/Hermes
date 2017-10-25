package parser.tree.expression;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.interfaces.Variable;
import parser.tree.symbolsTable.SymbolsTable;

public class IdNode extends ExpressionNode implements Variable {
    private final String name;
    private Type type;
    public IdNode(Location location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public Value interpret() throws SemanticException {
        return SymbolsTable.getInstance().getValue(name);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return SymbolsTable.getInstance().exist(name)
                ? SymbolsTable.getInstance().getType(name)
                : type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
