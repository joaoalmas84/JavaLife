package pt.isec.pa.javalife.model.data;

public abstract sealed class ElementoBase implements IElemento permits Inanimado, Flora, FaunaData {

    protected Area area;

    public void Elemento() {
        //area = new Area();
    }

    @Override
    public Area getArea() { return area;}

}
