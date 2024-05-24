package pt.isec.pa.javalife.model.data;

import java.util.Set;

abstract public sealed class Fauna
        extends ElementoBase
        implements IElementoComForca, Cloneable
        permits FaunaContext {
    private static double danoPorMovimento = 0.5;
    private static int nextId = 0;
    private int id;
    private double forca;
    private boolean isDead;
    private int matingCounter;
    private Area area;
    private double velocidade = 10;
    private double direcao = 270;

    public Fauna(double xi, double yi, double xf, double yf) {
        super();
        id = nextId++;
        forca = 50;
        isDead = false;
        matingCounter = 0;
        area = new Area(xi, yi, xf, yf);
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
    public void setForca(double forca) {
        if(this.forca + forca < 0) {
            this.forca = 0;
            isDead = true;
        }else if(this.forca + forca > 100) {
            this.forca = 100;
        } else {
            this.forca += forca;
        }
    }

    public void move(Set<IElemento> elementos) {

        double variacaoX = velocidade * Math.cos(direcao);
        double variacaoY = velocidade * Math.sin(direcao);

        Area novaArea = new Area(
                area.xi() + variacaoX,
                area.yi() + variacaoY,
                area.xf() + variacaoX,
                area.yf() + variacaoY);

        System.out.println("Fauna mov22222222222e:  " + variacaoX);
        for(IElemento elem : elementos) {
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(novaArea)) {
                direcao = Math.random() * 360;
                return ;
            }
        }

        System.out.println("Fauna move: " + novaArea.toString());
        area = novaArea;
        setForca(danoPorMovimento);
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
    public Fauna clone() throws CloneNotSupportedException{
            return (Fauna) super.clone();
    }

    @Override
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
