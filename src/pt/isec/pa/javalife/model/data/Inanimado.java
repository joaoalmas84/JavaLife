package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;

public final class Inanimado extends ElementoBase {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inanimado{");
        sb.append("id=").append(getId());
        sb.append(", area=").append(getArea());
        sb.append('}');
        return sb.toString();
    }
}
