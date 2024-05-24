package pt.isec.pa.javalife.model.data;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem, Cloneable{
    private ArrayList<IEvento> eventos;
    private static int nextId = 0;
    private int id;
    private int NumReproducoes;
    private double forca;
    private boolean isDead;
    private Area area;
    private Ecossistema ecossistema;
    private double dano;


    public Flora(double xi, double yi, double xf, double yf) {
        super();
        id = nextId++;
        NumReproducoes = 0;
        forca = 50;
        isDead = false;
        area = new Area(xi, yi, xf, yf);
        dano = -1.0;
    }

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void addEevnto(IEvento evento) {
        eventos.add(evento);
    }

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
    public double getForca() {return forca;}

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

    public boolean isDead() {
        return isDead;
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


    public void move(Set<IElemento> elementos){
        if(!isDead){
        setForca(5);
        reproduz(elementos);
        serComida(elementos);
        semEnergia();
        }
    }

    private void semEnergia() {
        if(forca <= 0){
            isDead = true;
        }
    }

    private void serComida(Set<IElemento> elementos) {
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA && elem.getArea().isOverlapping(area)){
                setForca(dano);
            }
        }
    }

    public boolean reproduz(Set<IElemento> elementos) {
        if (forca < 90) {
            return false;
        }

        Random random = new Random();
        int tentativas = 3;

        while (tentativas > 0) {
            Area areaAdjacente = area.getAriaAdjacente(random.nextInt(tentativas + 1));

            if (areaAdjacente == null) {
                tentativas--;
                continue;
            }

            boolean areaOcupada = elementos.stream().anyMatch(elem ->
                    (elem.getType() == Elemento.FLORA || elem.getType() == Elemento.INANIMADO) &&
                            elem.getArea().isOverlapping(areaAdjacente)
            );

            if (!areaOcupada) {
                ecossistema.addElemento(new Flora(areaAdjacente.xi(), areaAdjacente.yi(), areaAdjacente.xf(), areaAdjacente.yf()));
                NumReproducoes++;

                if (NumReproducoes == 3) {
                    isDead = true;
                }
                return true;
            }

            tentativas--;
        }

        return false;
    }

    public void setDano(double dano) {
        this.dano = dano;
    }
}
