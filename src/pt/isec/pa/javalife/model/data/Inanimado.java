package pt.isec.pa.javalife.model.data;

public final class Inanimado extends ElementoBase {
    private static int nextId = 0;
    private int id;
    private Area area;

    Inanimado(double xi, double yi, double xf, double yf) {
        area = new Area(xi, yi, xf, yf);
        id = nextId++;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Elemento getType() {
        return Elemento.INANIMADO;
    }

    @Override
    public Area getArea() {
        return area;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inanimado{");
        sb.append("id=").append(id);
        sb.append(", area=").append(/*Area.toString()*/ " dados da area");
        sb.append('}');
        return sb.toString();
    }
}
