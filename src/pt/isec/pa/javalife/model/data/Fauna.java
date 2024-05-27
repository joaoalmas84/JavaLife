package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.fsm.states.MovingState;

import java.util.Set;

public non-sealed class Fauna
        extends ElementoBase
        implements IElementoComImagem, IElementoComForca {
    IFaunaState atual;
    FaunaData data;

    public Fauna(double xi, double yi, double xf, double yf, Ecossistema e) {
        super(xi, yi, xf, yf);

        data = new FaunaData(e);
        atual = FaunaState.MOVING.getInstance(this, data);
    }

    // Dependency injection
    public Fauna(double xi, double yi, double xf, double yf, Ecossistema e, FaunaData data) {
        super(xi, yi, xf, yf);

        this.data = data;
        atual = new MovingState(this, data);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Transicao de Estados +-----------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void move_context() { atual.move(); }

    public void eat_context() { atual.eat(); }

    public void multiply_context() { atual.multiply(); }

    public void changeState(IFaunaState newState) { atual = newState; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public FaunaState _getState() { return atual.getState(); }

    @Override
    public int getId() { return data.getId(); }

    @Override
    public Elemento getType() { return data.getType(); }

    @Override
    public double getForca() {
        return data.getForca();
    }

    @Override
    public String getImagem() { return data.getImage(); }

    @Override
    public void setForca(double forca) { data.setForca(forca); }

    @Override
    public void setImagem(String imagem) { }

    public void setArea (Area area) { this.area = area; }

    @Override
    public String toString() {
        return data.toString(area);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Verificacoes +-------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------


    public boolean isDead() { return data.isDead(); }

}
