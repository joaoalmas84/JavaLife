package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem, Cloneable{

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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Flora{");
        sb.append("id=").append(getId());
        sb.append(", forca=").append(getForca());
        sb.append(", imagem=").append(getImagem());
        sb.append(", area=").append(getArea());
        sb.append('}');
        return sb.toString();
    }
    @Override
    public Flora clone() {
        try {
            return (Flora) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
