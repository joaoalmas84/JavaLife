package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.fsm.states.MovingState;

public non-sealed class FaunaContext extends Fauna {
    IFaunaState atual;

    public FaunaContext(double xi, double yi, double xf, double yf) {
        super(xi, yi, xf, yf);
        this.atual = new MovingState(this, this);
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
