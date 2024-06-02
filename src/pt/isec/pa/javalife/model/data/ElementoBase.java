package pt.isec.pa.javalife.model.data;

public abstract sealed class ElementoBase
        implements IElemento
        permits Inanimado, Flora, Fauna {

    protected Area area;

    public ElementoBase(double xi, double yi, double xf, double yf) {
        area = new Area(xi, yi, xf, yf);
    }

    @Override
    public Area getArea() { return area;}

    public void setArea(Area area){
        this.area=area;
    }

}
