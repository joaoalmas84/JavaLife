package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.fsm.states.MovingState;

public class FaunaContext {
    IFaunaState atual;
    Fauna data;

    public FaunaContext() {
        data = new Fauna();
        atual = FaunaState.MOVING.getInstance(this, data);
    }

    // dependency injection
    public FaunaContext(Fauna data) {
        this.data = data;
        this.atual = new MovingState(this, data);
    }

    void changeState(IFaunaState newState) { atual = newState; }

    // Transição de Estados
    public boolean move(double velocidade, double direcao) {
        return atual.move(velocidade, direcao);
    }

    public boolean eat() { return atual.eat(); }

    public boolean multiply() { return atual.multiply(); }

    // Getters
    public FaunaState getState() { return atual.getState(); }

}
