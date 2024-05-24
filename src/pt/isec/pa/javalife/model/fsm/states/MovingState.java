package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class MovingState extends FaunaStateAdapter {

    public MovingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public boolean move(double velocidade, double direcao) { return false; }

    @Override
    public boolean eat() {
        return false;
    }

    @Override
    public boolean multiply() {
        return false;
    }

    @Override
    public FaunaState getState() { return FaunaState.MOVING; }
}
