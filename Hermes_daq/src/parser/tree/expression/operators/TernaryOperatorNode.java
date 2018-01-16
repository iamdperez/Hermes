package parser.tree.expression.operators;

import parser.ParserUtils;
import parser.exeptions.SemanticException;
import parser.tree.Location;
import parser.tree.types.IntType;
import parser.tree.types.PinType;
import parser.tree.types.SetType;
import parser.tree.types.Type;
import parser.tree.values.Value;
import parser.tree.expression.ExpressionNode;
import serialCommunication.SerialCommException;

public class TernaryOperatorNode extends ExpressionNode {
    private final ExpressionNode truePart;
    private final ExpressionNode condition;
    private final ExpressionNode falsePart;

    public TernaryOperatorNode(Location location, ExpressionNode condition, ExpressionNode truePart, ExpressionNode falsePart) {
        super(location);
        this.truePart = truePart;
        this.condition = condition;
        this.falsePart = falsePart;
    }

    @Override
    public Value interpret() throws SemanticException, SerialCommException {
        Value trueValue = getTruePart().interpret();
        Value falseValue = getFalsePart().interpret();
        Value condition = getCondition().interpret();
        return (int)condition.getValue() > 0 ? trueValue.clone() : falseValue.clone();
    }

    @Override
    public Type evaluateSemantic() throws SemanticException {
        Type condition = getCondition().evaluateSemantic();
        if(!typeIsValid(condition)){
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Condition must be a [IntType, PinType, SetType] not "
                            +condition+".",getLocation())
            );
        }
        Type falseExp = getFalsePart().evaluateSemantic();
        Type trueExp = getTruePart().evaluateSemantic();

        validateIdNode(getFalsePart());
        validateIdNode(getTruePart());

        if(falseExp.getClass() != trueExp.getClass()){
            throw new SemanticException(
                    ParserUtils.getInstance().getLineErrorMessage("Return value must be equal between "
                            +trueExp+" and "+falseExp+".",getLocation())
            );
        }
        return trueExp;
    }

    boolean typeIsValid(Type t){
        return t instanceof IntType
                || t instanceof PinType
                || t instanceof SetType;
    }

    public ExpressionNode getTruePart() {
        return truePart;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ExpressionNode getFalsePart() {
        return falsePart;
    }
}
