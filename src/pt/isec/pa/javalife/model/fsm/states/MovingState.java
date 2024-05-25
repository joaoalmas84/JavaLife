package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class MovingState extends FaunaStateAdapter implements IFaunaState {

    public MovingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("MovingState");
        boolean res = data.move(elementos);
        if(!data.existemArvores() && !data.existemFauna()){
            return false;
        }

        if(data.getForca() <= 35 && data.existemArvores()){
            System.out.println("Looking for food");
            changeState(FaunaState.LOOKING_FOR_FOOD);
        }

        if(data.getForca() <= 35 && !data.existemArvores()){
            System.out.println("Hunting");
            changeState(FaunaState.HUNTING);
        }

        if(data.getForca() > 50){
            System.out.println("Chasing partner");
            changeState(FaunaState.CHASING_PARTNER);
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
    public FaunaState getState() { return FaunaState.MOVING; }
}
