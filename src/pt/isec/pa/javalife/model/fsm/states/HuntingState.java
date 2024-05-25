package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class HuntingState extends FaunaStateAdapter implements IFaunaState {

    public HuntingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("HuntingState");
        boolean res = data.hunting(elementos);
        if(data.getForca() <= 0){
            changeState(FaunaState.DEAD);
        }
        if(res){
            changeState(FaunaState.MOVING);
        }
        if(!data.existemArvores() && !data.existemFauna()){
            changeState(FaunaState.MOVING);
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
    public FaunaState getState() { return FaunaState.HUNTING; }
}
