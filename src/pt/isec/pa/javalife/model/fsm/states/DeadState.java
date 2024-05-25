package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.FaunaContext;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;


public class DeadState extends FaunaStateAdapter {

    public DeadState(FaunaContext context, Fauna data) {
        super(context, data);
    }

/*
    @Override
    public boolean move(Set<IElemento> elementos) {
        System.out.println("DeadState");
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
*/
    @Override
    public FaunaState getState() { return FaunaState.DEAD; }
}
