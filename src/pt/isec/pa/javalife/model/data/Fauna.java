package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;

public final class Fauna
        extends ElementoBase
        implements IElementoComForca {

    @Override
    public double getForca() {
        return 0;
    }

    @Override
    public void setForca(double forca) {}

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
