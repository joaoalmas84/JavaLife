package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class ChasingPartnerState extends FaunaStateAdapter implements IFaunaState {

    public ChasingPartnerState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("ChasingPartnerState");
        boolean res = data.multiply(elementos);
        if(data.getForca() <= 0){
            changeState(FaunaState.DEAD);
        }
        if(data.getForca() <= 50 && data.getForca() >= 35){
            changeState(FaunaState.MOVING);
        }
        if (data.getForca() < 35){
            changeState(FaunaState.LOOKING_FOR_FOOD);
        }

        return res;
    }
/*
    @Override
    public boolean eat() {
        return false;
    }

    @Override
    public boolean multiply() {
        return false;
    }
*/
    @Override
    public FaunaState getState() { return FaunaState.CHASING_PARTNER; }
}
