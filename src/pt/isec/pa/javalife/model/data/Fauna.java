package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.Area;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.states.IFaunaState;

public final class Fauna
        extends ElementoBase
        implements IElementoComForca {

    private static int nextId = 0;
    private int id;
    private double forca;
    private boolean isDead;
    private int matingCounter;

    public Fauna() {
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

    public boolean move(double velocidade, double direcao) {
        // TODO
        forca -= 0.5;
        return false;
    }

    public boolean eat() {
        // TODO
        return false;
    }

    public boolean multiply() {
        // TODO
        return false;
    }
}
