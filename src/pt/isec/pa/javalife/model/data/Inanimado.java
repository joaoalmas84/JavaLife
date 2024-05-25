package pt.isec.pa.javalife.model.data;

public final class Inanimado extends ElementoBase {
    private static int nextId = 0;
    private final int id;
    private final Area area;
    private final boolean podeRemove;

    Inanimado(double xi, double yi, double xf, double yf, boolean podeRemove) {
        area = new Area(xi, yi, xf, yf);
        id = nextId++;
        this.podeRemove = podeRemove;
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
        return "Inanimado{" +
                "id=" + id +
                ", area=" + area +
                ", podeRemove=" + podeRemove +
                '}';
    }

    public boolean podeRemove() {
        return podeRemove;
    }
}
