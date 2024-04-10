package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;

public final class Flora
        extends ElementoBase
        implements IElementoComForca, IElementoComImagem {

    @Override
    public double getForca() {
        return 0;
    }

    @Override
    public void setForca(double forca) {}

    @Override
    public String getImagem() {
        return null;
    }

    @Override
    public void setImagem(String imagem) {}

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Elemento getType() {
        return null;
    }

    @Override
    public Area getArea() {
        return null;
    }
}
