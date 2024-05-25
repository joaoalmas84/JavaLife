package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class LookingForFoodState extends FaunaStateAdapter implements IFaunaState {

    public LookingForFoodState(Fauna context, FaunaData data) {
        super(context, data);
    }


    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("LookingForFoodState");
        boolean res = data.lookingForFoog(elementos);
        if(data.getForca() <= 0){
            changeState(FaunaState.DEAD);
        }
        if(res){
            changeState(FaunaState.EATING);
        }
        if(!data.existemArvores()){
            changeState(FaunaState.HUNTING);
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
    public FaunaState getState() { return FaunaState.LOOKING_FOR_FOOD; }
}
