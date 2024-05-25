package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;

import java.util.Set;

abstract public class FaunaStateAdapter implements IFaunaState {
    protected Fauna context;
    protected FaunaData data;

    protected FaunaStateAdapter(Fauna context, FaunaData data) {
        this.context = context;
        this.data = data;
    }

    public void changeState(FaunaState newState) {
        context.changeState(newState.getInstance(context, data));
    }

    @Override
    public boolean move(Set<IElemento> elementos) {
        return false;
    }

    @Override
    public boolean eat() {
        return false;
    }

    @Override
    public boolean multiply() {
        return false;
    }
}
