package pt.isec.pa.javalife.model.data;

import java.util.Random;
import java.util.Set;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem{
    private static int nextId = 0;
    private final int id;
    private int NumReproducoes;
    private double forca;
    private boolean isDead;
    private Ecossistema ecossistema;

    // Construtor
    public Flora(double xi, double yi, double xf, double yf) {
        super(xi, yi, xf, yf);
        id = nextId++;
        NumReproducoes = 0;
        forca = 50;
        isDead = false;
        area = new Area(xi, yi, xf, yf);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------
    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Flora.nextId = nextId;
    }

    @Override
    public String getImagem() {
        return null;
    }

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

    public double getForca() {
        return forca;
    }

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    @Override
    public void addForca(double forca) {
        if(forca + this.forca < 0){
            this.forca = 0;
            isDead = true;
        }else if(forca + this.forca > 100){
            this.forca = 100;
        }else{
            this.forca += forca;
        }
    }

    @Override
    public void setImagem(String imagem) {}

    // +----------------------------------------------------------------------------------------------------------------
    // + Outras +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean isDead() {
        return isDead;
    }

    private void semEnergia() {
        isDead = forca <= 0;
    }

    public void evolve(Set<IElemento> elementos){
        if(!isDead){
            if(ecossistema.isEventoSol()){
                addForca(Ecossistema.regenFlora*2);
            }
            else
                addForca(Ecossistema.regenFlora);
            reproduz(elementos);
            semEnergia();
        }
    }

    void serComida() {
        addForca(-Ecossistema.danoFauna);
    }

    public boolean reproduz(Set<IElemento> elementos) {
        if (forca < 90) {
            return false;
        }
        if(NumReproducoes>=2){
            return false;
        }
        Random random = new Random();
        int tentativas = 3;

        while (tentativas > 0) {
            Area areaAdjacente = area.getAreaAdjacente(random.nextInt(3 + 1));

            if (areaAdjacente == null) {
                tentativas--;
                continue;
            }

            boolean areaOcupada = elementos.stream().anyMatch(elem ->
                    ((elem.getType() == Elemento.FLORA || elem.getType() == Elemento.INANIMADO)) &&
                            elem.getArea().isOverlapping(areaAdjacente)
            );

            if (!areaOcupada) {
                ecossistema.addElemento(new Flora(areaAdjacente.xi(), areaAdjacente.yi(), areaAdjacente.xf(), areaAdjacente.yf()));
                forca=60;
                NumReproducoes++;

                return true;
            }

            tentativas--;
        }

        return false;
    }

    @Override
    public String toString() {
        String sb = "Flora{" +
                "id=" + id +
                ", forca=" + forca +
                ", Area=" + area +
                ", isDead=" + isDead +
                ", NumReproducoes=" + NumReproducoes +
                '}';
        return sb;
    }

    public int getNumReproducoes() {
        return NumReproducoes;
    }

    public void setNumReproducoes(int NumReproducoes) {
        this.NumReproducoes = NumReproducoes;
    }

    public void setArea(double xi, double yi, double xf, double yf) {
        System.out.println("Flora.setArea" + xi + " " + yi + " " + xf + " " + yf);
        this.area = new Area(xi, yi, xf, yf);
        System.out.println("Flora.setArea" + area);
    }
}
