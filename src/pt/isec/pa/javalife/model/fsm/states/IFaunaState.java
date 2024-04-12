package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.fsm.FaunaState;

public interface IFaunaState {
    boolean move(double velocidade, double direcao);
    boolean eat();
    boolean multiply();

    FaunaState getState();
}
