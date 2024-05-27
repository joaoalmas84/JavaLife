package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.fsm.FaunaState;

public interface IFaunaState {
    void act();

    FaunaState getState();
}
