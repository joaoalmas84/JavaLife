package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class EatingState
        extends FaunaStateAdapter
        implements IFaunaState {

    public EatingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("EatingState");
        boolean res = data.eat(elementos);

        if (!res && data.getForca() < 80 && !data.existemArvores()) {
            changeState(FaunaState.HUNTING);
        }

        if (!res && data.getForca() < 80) {
            changeState(FaunaState.LOOKING_FOR_FOOD);
        }

        if (!res && data.getForca() >= 80) {
            changeState(FaunaState.MOVING);
        }

        if (data.getForca() >= 100) {
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
    public FaunaState getState() { return FaunaState.EATING; }
}
