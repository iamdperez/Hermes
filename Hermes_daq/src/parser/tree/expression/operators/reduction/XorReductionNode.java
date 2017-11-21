package parser.tree.expression.operators.reduction;

import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.Type;
import parser.tree.values.IntValue;
import parser.tree.values.Value;

import java.util.ArrayList;

public class XorReductionNode extends ReductionOperators {
    private final ArrayList<IdNode> ids;
    public XorReductionNode(Location location, ArrayList<IdNode> ids) {
        super(location);
        this.ids = ids;
    }

    @Override
    public Value interpret() throws SemanticException {
        Value firstItemVal = SymbolsTable.getInstance().getVariableValue(ids.get(0).getName(),ids.get(0).getLocation());
        int result = (int)firstItemVal.getValue();
        for(int i = 1; i < ids.size(); i++){
            IdNode item = ids.get(i);
            Value itemVal = SymbolsTable.getInstance().getVariableValue(item.getName(),item.getLocation());
            int val = (int)itemVal.getValue();
            result = result ^ val;
        }
        return new IntValue(result);
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        return validateSemantic("Xor", getIds());
    }

    public ArrayList<IdNode> getIds() {
        return ids;
    }
}