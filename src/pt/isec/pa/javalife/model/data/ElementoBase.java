package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;

public abstract sealed class ElementoBase implements IElemento permits Inanimado, Flora, Fauna {

    protected Area area;

    public void Elemento() {
        //area = new Area();
    }

    @Override
    public Area getArea() { return area;}

}
