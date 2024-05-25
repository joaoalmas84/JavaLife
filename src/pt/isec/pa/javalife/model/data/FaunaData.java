package pt.isec.pa.javalife.model.data;

import java.util.Set;

public class FaunaData {
    private static int nextId = 0;
    private int id;
    private double forca;
    private boolean isDead;
    private int matingCounter;
    private double velocidade = 10;
    private double direcao;
    private double dano = 1;
    private Ecossistema ecossistema;

    // +----------------------------------------------------------------------------------------------------------------
    // +----------------------------------------------+ Private +-------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    // Construtor
    public FaunaData(Ecossistema e) {
        super();
        direcao = Math.random() * 360;
        id = nextId++;
        forca = 50;
        isDead = false;
        matingCounter = 0;
        ecossistema = e;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public int getMatingCounter() { return matingCounter; }

    public int getId() { return id; }

    public Elemento getType() {
        return Elemento.FAUNA;
    }

    public double getForca() { return forca; }

    public double getVelocidade() { return velocidade; }

    public double getDirecao() { return direcao; }

    private Fauna getMaisForte(Set<IElemento> elementos){
        Fauna fauna = null;
        double forcaMaisForte = -100;
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA && elem.getId() != id){
                if(((Fauna)elem).getForca() > forcaMaisForte){
                    forcaMaisForte = ((Fauna)elem).getForca();
                    fauna = (Fauna) elem;
                }
            }
        }
        return  fauna;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Setters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

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

    public void setDirecao(double direcao) { this.direcao = direcao; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Outras +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void danoPorMovimento() {
        forca -= 0.5;
        if (forca <= 0) {isDead = true;}
    }

    public boolean existemArvores(){
        return ecossistema.existemArvores();
    }

    public boolean existemFauna(){
        if(ecossistema == null) return false;
        return ecossistema.existemAnimais();
    }

    public boolean isDead() { return isDead; }

    public boolean eat(Set<IElemento> elementos, Area area) {
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                setForca(dano);
                ((Flora)elem).setForca(-dano);
                return true;
            }
        }
        return false;
    }

    public boolean lookingForFood(Set<IElemento> elementos, Area area) {
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
        Fauna fauna;
        if((fauna = getMaisForte(elementos)) == null){ return false; }


        if(fauna.getArea().distanceTo(area) < 5){
            matingCounter++;
            if(matingCounter == 10){
                matingCounter = 0;
                ecossistema.addElemento(new Fauna(area.xi(), area.yi(), area.xf(), area.yf()));
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

    public boolean hunting(Set<IElemento> elementos) {
        Fauna fauna;
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

    private Fauna getMaisFraco(Set<IElemento> elementos) {
        Fauna fauna = null;
        double forcaMaisFraco = Double.MAX_VALUE;
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA && elem.getId() != id){
                if(((Fauna)elem).getForca() < forcaMaisFraco){
                    forcaMaisFraco = ((Fauna)elem).getForca();
                    fauna = (Fauna) elem;
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
    public FaunaData clone() throws CloneNotSupportedException{
            return (FaunaData) super.clone();
    }

    public Area getArea() {
        return area;
    }

}
