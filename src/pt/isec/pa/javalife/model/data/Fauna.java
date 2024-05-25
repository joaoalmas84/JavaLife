package pt.isec.pa.javalife.model.data;

import java.util.Set;

abstract public sealed class Fauna extends ElementoBase implements IElementoComForca, Cloneable permits FaunaContext {

    protected static double danoPorMovimento = -0.5;
    protected static int nextId = 0;
    protected int id;
    protected double forca;
    protected boolean isDead;
    protected int matingCounter;
    protected Area area;
    protected double velocidade = 10;
    protected double direcao;
    protected double dano = 1;
    protected Ecossistema ecossistema;

    public Fauna(double xi, double yi, double xf, double yf) {
        super();
        direcao = Math.random() * 360;
        id = nextId++;
        forca = 50;
        isDead = false;
        matingCounter = 0;
        area = new Area(xi, yi, xf, yf);
    }

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

    public boolean existemArvores(){
        return ecossistema.existemArvores();
    }

    public boolean existemFauna(){
        if(ecossistema == null) return false;
        return ecossistema.existemAnimais();
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

    public boolean move(Set<IElemento> elementos) {
        double old_Direcao = direcao;
        Area novaArea;


        double variacaoX = velocidade * Math.cos(direcao);
        double variacaoY = velocidade * Math.sin(direcao);

        novaArea = new Area(
                area.xi() + variacaoX,
                area.yi() + variacaoY,
                area.xf() + variacaoX,
                area.yf() + variacaoY);

        for(IElemento elem : elementos) {
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(novaArea)) {
                    direcao = (direcao + 90) % 360;
                    return false;
            }
        }

        area = novaArea;
        setForca(danoPorMovimento);
        if(forca<=0){
            isDead=true;
        }
        return true;
    }

    public boolean eat(Set<IElemento> elementos) {
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                setForca(dano);
                ((Flora)elem).setForca(-dano);
                return true;
            }
        }
        return false;
    }

    public boolean lookingForFoog(Set<IElemento> elementos) {
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                return true;
            }
        }

        IElemento floraMaisProxima;

        if ((floraMaisProxima = getFloraMaisProcima(elementos)) == null) {
            return false;
        }
        direcao = area.angleTo(floraMaisProxima.getArea());

        for(int i = 0; i < 7 && !move(elementos); i++) {
            direcao = Math.random() * 360;
        }
        return false;

    }

    private IElemento getFloraMaisProcima(Set<IElemento> elementos){
        IElemento floraMaisProxima = null;
        double distancia = Double.MAX_VALUE;
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FLORA) {
                if(elem.getArea().distanceTo(area) < distancia){
                    distancia = elem.getArea().distanceTo(area);
                    floraMaisProxima = elem;
                }
            }
        }
        return floraMaisProxima;
    }

    public boolean multiply(Set<IElemento> elementos) {
        FaunaContext fauna;
        if((fauna = getMaisForte(elementos)) == null){ return false; }


        if(fauna.getArea().distanceTo(area) < 5){
            matingCounter++;
            if(matingCounter == 10){
                matingCounter = 0;
                ecossistema.addElemento(new FaunaContext(area.xi(), area.yi(), area.xf(), area.yf()));
                setForca(-25);
                return true;
            }
        }

        direcao = area.angleTo(fauna.getArea());
        for(int i = 0; i < 7 && !move(elementos); i++) {
            direcao = Math.random() * 360;
        }

        return false;
    }

    private FaunaContext getMaisForte(Set<IElemento> elementos){
        FaunaContext fauna = null;
        double forcaMaisForte = -100;
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA && elem.getId() != id){
                if(((Fauna)elem).getForca() > forcaMaisForte){
                    forcaMaisForte = ((Fauna)elem).getForca();
                    fauna = (FaunaContext) elem;
                }
            }
        }
        return  fauna;
    }

    public boolean hunting(Set<IElemento> elementos) {
        FaunaContext fauna;
        if((fauna = getMaisFraco(elementos)) == null){ return false; }

        if(fauna.getArea().distanceTo(area) < area.width()){
            if(fauna.getForca() < forca){
                setForca(-10);
                if(!isDead){
                    setForca(fauna.getForca());
                }
                setForca(-999);
            }
            return true;
        }

        direcao = area.angleTo(fauna.getArea());
        for(int i = 0; i < 7 && !move(elementos); i++) {
            direcao = Math.random() * 360;
        }

        return false;
    }

    private FaunaContext getMaisFraco(Set<IElemento> elementos) {
        FaunaContext fauna = null;
        double forcaMaisFraco = Double.MAX_VALUE;
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA && elem.getId() != id){
                if(((Fauna)elem).getForca() < forcaMaisFraco){
                    forcaMaisFraco = ((Fauna)elem).getForca();
                    fauna = (FaunaContext) elem;
                }
            }
        }
        return  fauna;
    }

    @Override
    public String toString() {
        return "Fauna{" +
                "id=" + id +
                ", forca=" + forca +
                ", isDead=" + isDead +
                ", area=" + area +
                ", velocidade=" + velocidade +
                ", direcao=" + direcao +
                ", dano=" + dano +
                ", matingCounter=" + matingCounter +
                '}';
    }

    @Override
    public Fauna clone() throws CloneNotSupportedException{
            return (Fauna) super.clone();
    }

    @Override
    public Area getArea() {
        return area;
    }

}
