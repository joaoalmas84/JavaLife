package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;

import java.util.Set;

public interface IFaunaState {
    void move();
    void eat();
    void multiply();

    FaunaState getState();
}
