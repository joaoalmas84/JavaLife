package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.FaunaContext;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;

abstract public class FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected Fauna data;

    protected FaunaStateAdapter(FaunaContext context, Fauna data) {
        this.context = context;
        this.data = data;
    }

    public void changeState(IFaunaState newState) {
        context.changeState(newState);
    }

    @Override
    public boolean move(double velocidade, double direcao) {
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
