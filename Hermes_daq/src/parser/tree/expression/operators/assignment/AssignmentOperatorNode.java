package parser.tree.expression.operators.assignment;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.expression.IdNode;
import parser.tree.symbolsTable.Symbol;
import parser.tree.symbolsTable.SymbolsTable;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.interfaces.Variable;
import parser.tree.expression.ExpressionNode;
import parser.tree.expression.operators.AssignmentOperator;

public class AssignmentOperatorNode extends AssignmentOperator {

    public AssignmentOperatorNode(Location location){
        super(location);
    }

    public AssignmentOperatorNode(Location location, Variable variable, ExpressionNode rightNode) {
        super(location, variable, rightNode);
    }

    @Override
    public Value interpret() throws SemanticException {
        Value rightValue = getRightNode().interpret();
        Object value = rightValue.getValue();
        IdNode id = (IdNode)getVariable();
        Value idValue = SymbolsTable.getInstance().getVariableValue(id.getName());
        idValue.setValue(value);
        SymbolsTable.getInstance().setVariableValue(id.getName(),idValue);
        return null;
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        IdNode id = (IdNode)getVariable();
        if(!SymbolsTable.getInstance().variableExist(id.getName())){
            throw new SemanticException(errorMessage(id));
        }

        Symbol idVariable = SymbolsTable.getInstance().getVariable(id.getName());
        Type rightType = getRightNode().evaluateSemantic();

        if((getRightNode() instanceof IdNode) && rightType == null){
            throw new SemanticException(errorMessage((IdNode) getRightNode()));
        }

        if(idVariable == null){
            throw new SemanticException(errorMessage(id));
        }

        if(idVariable.getType() == rightType)
            return idVariable.getType();

        throw new SemanticException(
                ParserUtils.getLineErrorMessage("Assignment is not supported between "+ idVariable.getType()
                        + " and " + rightType + " types.",
                        getLocation()));
    }

    private String errorMessage(IdNode idNode){
        return ParserUtils.getLineErrorMessage("Variable `"+idNode.getName()
                        +"` must be declared before",
                idNode.getLocation());
    }
}
