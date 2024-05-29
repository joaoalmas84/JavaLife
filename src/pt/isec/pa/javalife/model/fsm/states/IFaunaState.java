package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.fsm.FaunaState;

import java.io.Serializable;

public interface IFaunaState extends Serializable {
    void act();

    FaunaState getState();
}
