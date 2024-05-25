package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;

import java.util.Set;

public interface IFaunaState {
    boolean move(Set<IElemento> elementos);
    boolean eat();
    boolean multiply();

    FaunaState getState();
}
