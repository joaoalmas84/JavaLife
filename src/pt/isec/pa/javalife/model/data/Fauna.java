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

    public Fauna(double xi, double yi, double xf, double yf) {
        super(xi, yi, xf, yf);

        data = new FaunaData(area);
        atual = FaunaState.MOVING.getInstance(this, data);
    }

    public void changeState(IFaunaState newState) { atual = newState; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Transicoes de Estados +----------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean _move(Set<IElemento> elementos) {

        return atual.move(elementos);
    }

    /*public boolean _eat() { return atual.eat(); }

    public boolean _multiply() { return atual.multiply(); }

    // Getters
    public FaunaState _getState() { return atual.getState(); }
*/

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    @Override
    public int getId() { return data.getId(); }

    @Override
    public Elemento getType() { return data.getType(); }

    @Override
    public double getForca() {
        return data.getForca();
    }

    @Override
    public String getImagem() {  }

    @Override
    public void setForca(double forca) {  }

    @Override
    public void setImagem(String imagem) {  }

    // +----------------------------------------------------------------------------------------------------------------
    // + Acoes +-------------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean move(Set<IElemento> elementos) {
        Area novaArea;
        double newDirecao;

        double variacaoX = data.getVelocidade() * Math.cos(data.getVelocidade());
        double variacaoY = data.getVelocidade() * Math.sin(data.getVelocidade());

        novaArea = new Area(
                area.xi() + variacaoX,
                area.yi() + variacaoY,
                area.xf() + variacaoX,
                area.yf() + variacaoY);

        for( IElemento elem : elementos ) {
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(novaArea)) {
                newDirecao = (data.getDirecao() + 90) % 360;
                data.setDirecao(newDirecao);
                return false;
            }
        }

        area = novaArea;
        data.danoPorMovimento();

        if (data.getForca() <= 0) { ; }

        return true;
    }


}
