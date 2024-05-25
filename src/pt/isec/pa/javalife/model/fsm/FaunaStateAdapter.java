package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.FaunaContext;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;

import java.util.Set;

abstract public class FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected Fauna data;

    protected FaunaStateAdapter(FaunaContext context, Fauna data) {
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
