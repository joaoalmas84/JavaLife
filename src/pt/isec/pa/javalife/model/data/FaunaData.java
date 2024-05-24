package pt.isec.pa.javalife.model.data;

abstract public sealed class FaunaData
        extends ElementoBase
        implements IElementoComForca, Cloneable
        permits Fauna {

    private static int nextId = 0;
    private int id;
    private double forca;
    private boolean isDead;
    private int matingCounter;

    public FaunaData() {
        super();
        id = nextId++;
        forca = 50;
        isDead = false;
        matingCounter = 0;
    }

    public boolean isDead() { return isDead; }

    public int getMatingCounter() { return matingCounter; }

    @Override
    public int getId() { return id; }

    @Override
    public Elemento getType() {
        return Elemento.FAUNA;
    }

    @Override
    public double getForca() { return forca; }

    @Override
    public void setForca(double forca) { this.forca = forca; }

    public void move(double velocidade, double direcao) {
        forca -= 0.5;

        double variacaoX=velocidade*Math.cos(Math.toRadians(direcao));
        double variacaoY=velocidade*Math.sin(Math.toRadians(direcao));

        //double cima = area.cima()-variacaoY;
        //double esquerda = area.esquerda()+variacaoX;
        //double baixo = area.baixo()+variacaoY;
        //double direita = area.direita()-variacaoX;
        //area=new Area(cima,esquerda,baixo,direita);
        if(forca<=0){
            isDead=true;
        }
    }

    public boolean eat() {
        return false;
    }

    public boolean multiply() {
        // TODO
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fauna{");
        sb.append("id=").append(id);
        sb.append(", forca=").append(forca);
        sb.append(", isDead=").append(isDead);
        sb.append(", matingCounter=").append(matingCounter);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public FaunaData clone() throws CloneNotSupportedException{
            return (FaunaData) super.clone();
    }
}
