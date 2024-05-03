package pt.isec.pa.javalife.model.data;

import java.util.ArrayList;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem, Cloneable{
    private ArrayList<IEvento> eventos;
    private static int nextId = 0;
    private int id;
    private int NumReproducoes;
    private double forca;
    private boolean isDead;
    private Area area;


    public Flora() {
        super();
        id = nextId++;
        NumReproducoes = 0;
        forca = 50;
        isDead = false;
        //area = new Area();
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void addEevnto(IEvento evento) {
        eventos.add(evento);
    }

    @Override
    public double getForca() {return forca;}

    @Override
    public void setForca(double forca) {
        if(forca + this.forca < 0){
            this.forca = 0;
        }else if(forca + this.forca > 100){
            this.forca = 100;
        }else{
            this.forca += forca;
        }
    }

    @Override
    public String getImagem() {
        return null;
    }

    @Override
    public void setImagem(String imagem) {}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Elemento getType() {
        return Elemento.FLORA;
    }

    @Override
    public Area getArea() {
        return area;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Flora{");
        sb.append("id=").append(id);
        sb.append(", forca=").append(forca);
        sb.append(", Area=").append("Area hahahahaha");
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
