package pt.isec.pa.javalife.model.data;

public final class Inanimado extends ElementoBase {
    private static int nextId = 0;
    private final int id;
    private final boolean podeRemove;

    // Construtor
    Inanimado(double xi, double yi, double xf, double yf, boolean podeRemove) {
        super(xi, yi, xf, yf);
        area = new Area(xi, yi, xf, yf);
        id = nextId++;
        this.podeRemove = podeRemove;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Inanimado.nextId = nextId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Elemento getType() {
        return Elemento.INANIMADO;
    }

    public void setArea(Area area) {
        this.area = area;
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
