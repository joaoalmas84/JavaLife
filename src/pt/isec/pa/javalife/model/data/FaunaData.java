package pt.isec.pa.javalife.model.data;

import java.io.Serializable;
import java.util.Objects;

public class FaunaData implements Serializable {
    private static int nextId = 0;
    private static double danoPorMoviemnto = 0.5;

    private int id;
    private double forca;

    private boolean isDead;

    private int matingCounter;

    private double velocidade;
    private double direcao;

    private String image;
    private Ecossistema ecossistema;

    // +----------------------------------------------------------------------------------------------------------------
    // +----------------------------------------------+ Public +--------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    // Construtor
    public FaunaData(Ecossistema e) {
        super();

        id = nextId++;
        forca = 50;
        isDead = false;

        matingCounter = 0;

        velocidade = 10;
        direcao = Math.random() * (2 * Math.PI);

        ecossistema = e;
        image = "guepardo.png";
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public static int getNextId() { return nextId; }

    public int getMatingCounter() { return matingCounter; }

    public int getId() { return id; }

    public Elemento getType() {
        return Elemento.FAUNA;
    }

    public double getForca() { return forca; }

    public double getVelocidade() { return velocidade; }

    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

    public double getDirecao() { return direcao; }


    public String getImage() { return image; }



    // +----------------------------------------------------------------------------------------------------------------
    // + Setters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------
    public String setImage(String image) { return this.image = image; }

    public static void setNextId(int nextId) { FaunaData.nextId = nextId; }

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

    public void addForca(double forca) {
        if ((this.forca + forca) < 0) {
            this.forca = 0;
            isDead = true;
        } else if ((this.forca + forca) > 100) {
            this.forca = 100;
        } else {
            this.forca += forca;
        }
    }

    public void setDirecao(double direcao) { this.direcao = direcao; }

    public void setMatingCounter(int val) { this.matingCounter = val; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Acoes +--------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public Area move(Area area) {
        Area novaArea;
        double newDirecao;

        double variacaoX = velocidade * Math.cos(direcao);
        double variacaoY = velocidade * Math.sin(direcao);

        novaArea = new Area(
                area.xi() + variacaoX,
                area.yi() + variacaoY,
                area.xf() + variacaoX,
                area.yf() + variacaoY);

        for (IElemento elem : ecossistema.getElementos()) {
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(novaArea)) {
                newDirecao = Math.random() * 2*Math.PI;
                direcao = newDirecao;

                return new Area(-1, -1, -1, -1);
            }
        }

        danoPorMovimento();

        return novaArea;
    }

    // Success -> encontrou flora
    public Area act_lookingForFood(Area area, Boolean[] success) {
        Flora floraMaisProxima;
        Area novaArea;

        for (IElemento elem : ecossistema.getElementos()) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                success[0] = true;
                return area;
            }
        }

        floraMaisProxima = ecossistema.getFloraMaisProxima(area);
        if (floraMaisProxima == null) {
            success[0] = false;
            return new Area(-1, -1, -1, -1);
        }

        direcao = area.angleTo(floraMaisProxima.getArea());

        novaArea = move( area);

        if (novaArea.isInvalid()) {
            direcao = Math.random() * (2 * Math.PI);
            novaArea = move(area);
        }

        success[0] = false;
        return novaArea;
    }

    public Area act_hunting(Area area) {
        Fauna fauna;
        Area novaArea;

        fauna = ecossistema.getMaisFraco(id);
        if (fauna == null) {
            return new Area(-1,-1,-1,-1);
        }

        if (fauna.getArea().isOverlapping(area)) {
            if (fauna.getForca() < forca) {
                addForca(-10);
                fauna.addForca(-999);
                if (isDead) {
                    return new Area(-1,-1,-1,-1);
                } else {
                    addForca(fauna.getForca());
                    return area;
                }
            } else {
                addForca(-999);
                return new Area(-1,-1,-1,-1);
            }
        } else {
            direcao = area.angleTo(fauna.getArea());

            novaArea = move(area);

            if( novaArea.isInvalid()) {
                direcao = Math.random() * 2*Math.PI;
                return move(area);
            }
            return novaArea;
        }
    }

    public Area act_chasingPartner(Area area, boolean[] multiplied) {
        Fauna fauna;
        Area newArea;

        fauna = ecossistema.getMaisForte(id);
        if (fauna == null) {
            return new Area(-1,-1,-1,-1);
        }

        if (fauna.getArea().distanceTo(area) < 5) {
            matingCounter++;
            if (matingCounter == 10) {
                matingCounter=0;
                multiply(area);
                multiplied[0]=true;
            }
            else
                multiplied[0]=false;
            return area;
        }
        else {
            matingCounter=0;
        }

        direcao = area.angleTo(fauna.getArea());

        newArea = move(area);

        if(newArea.isInvalid()) {
            direcao = Math.random() * 2*Math.PI;
            return move(area);
        }
        return newArea;
    }

    public boolean act_eat(Area area) {
        Flora erva;

        for (IElemento elem : ecossistema.getElementos()) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                erva = (Flora)elem;
                addForca(Ecossistema.danoFauna);
                erva.serComida();
                return !erva.isDead();
            }
        }

        return false;
    }

    public void multiply(Area area) {
        addForca(-25);
        ecossistema.addElemento(new Fauna(area.xi(), area.yi(), area.xf(), area.yf(), ecossistema));
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Verificacoes +-------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean existemArvores(){
        return ecossistema.existemArvores();
    }

    public boolean existeFauna(){
        if(ecossistema == null) return false;
        return ecossistema.existemAnimais();
    }

    public boolean existePartnerPrey(){
        if(ecossistema==null) return false;
        return ecossistema.existemPartners();
    }

    public boolean isDead() {return forca <= 0; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Outras +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void danoPorMovimento() {
        forca -= danoPorMoviemnto;
        if (forca <= 0) {isDead = true;}
    }

    public String toString(Area area) {
        return "Fauna{" +
                "id=" + id +
                ", forca=" + forca +
                ", isDead=" + isDead +
                ", area=" + area +
                ", velocidade=" + velocidade +
                ", direcao=" + direcao +
                ", matingCounter=" + matingCounter +
                '}';
    }

    @Override
    public FaunaData clone() throws CloneNotSupportedException{
        return (FaunaData) super.clone();
    }

}
