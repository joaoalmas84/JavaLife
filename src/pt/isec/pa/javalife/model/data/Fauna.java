package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.fsm.states.MovingState;

public non-sealed class Fauna extends FaunaData {
    IFaunaState atual;

    public Fauna() {
        super();
        atual = FaunaState.MOVING.getInstance(this, this);
    }

    // dependency injection
    public Fauna(FaunaData data) {
        this.atual = new MovingState(this, data);
    }

    public void changeState(IFaunaState newState) { atual = newState; }

    // Transição de Estados
    public boolean _move(double velocidade, double direcao) {

        return atual.move(velocidade, direcao);
    }

    public boolean _eat() { return atual.eat(); }

    public boolean _multiply() { return atual.multiply(); }

    // Getters
    public FaunaState _getState() { return atual.getState(); }

}
