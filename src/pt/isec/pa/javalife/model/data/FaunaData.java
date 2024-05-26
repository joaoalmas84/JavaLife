package pt.isec.pa.javalife.model.data;

import java.util.Set;

public class FaunaData {
    private static int nextId = 0;
    private static double dano = 1;
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
        direcao = Math.random() * 360;

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

    public double getDano() { return dano; }

    public String getImage() { return image; }

    // +----------------------------------------------------------------------------------------------------------------
    // + Setters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }

    public void setForca(double forca) {
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

        double variacaoX = velocidade * Math.cos(velocidade);
        double variacaoY = velocidade * Math.sin(velocidade);

        novaArea = new Area(
                area.xi() + variacaoX,
                area.yi() + variacaoY,
                area.xf() + variacaoX,
                area.yf() + variacaoY);

        for (IElemento elem : ecossistema.getElementos()) {
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(novaArea)) {
                newDirecao = (direcao + 90) % 360;
                direcao = newDirecao;

                return new Area(-1, -1, -1, -1);
            }
        }

        danoPorMovimento();

        return novaArea;
    }

    // Success -> encontrou flora
    public Area move_lookingForFood(Area area, Boolean[] success) {
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
            direcao = Math.random() * 360;
            novaArea = move(area);
        }

        success[0] = false;
        return novaArea;
    }

    public Area move_hunting(Area area) {
        Fauna fauna;
        Area novaArea;

        fauna = ecossistema.getMaisFraco(id);
        if (fauna == null) {
            return new Area(-1,-1,-1,-1);
        }

        if (fauna.getType() == Elemento.FAUNA && fauna.getArea().isOverlapping(area)) {
            if (fauna.getForca() < forca) {
                setForca(-10);
                fauna.setForca(-999);
                if (isDead) {
                    return new Area(-1,-1,-1,-1);
                } else {
                    setForca(fauna.getForca());
                    return area;
                }
            } else {
                setForca(-999);
                return new Area(-1,-1,-1,-1);
            }
        } else {
            direcao = area.angleTo(fauna.getArea());

            novaArea = move(area);

            for(int i = 0; i < 7 && move(area).isInvalid(); i++) {
                direcao = Math.random() * 360;
            }

            return new Area(-1,-1,-1,-1);
        }
    }

    public Area move_chasingPartner(Area area) {
        Fauna fauna;
        Area newArea = area;

        fauna = ecossistema.getMaisForte(id);
        if (fauna == null) {
            return new Area(-1,-1,-1,-1);
        }

        if (fauna.getArea().distanceTo(area) < 5) {
            matingCounter++;
            if (matingCounter == 10) {
                multiply(area);
            }
            return area;
        }

        direcao = area.angleTo(fauna.getArea());

        move(area);

        for(int i = 0; i < 7 && move(area).isInvalid(); i++) {
            direcao = Math.random() * 360;
        }

        return new Area(-1,-1,-1,-1);
    }

    public boolean eat(Area area) {
        Flora erva;

        for (IElemento elem : ecossistema.getElementos()) {
            if (elem.getType() == Elemento.FLORA && elem.getArea().isOverlapping(area)) {
                erva = (Flora)elem;
                setForca(dano);
                erva.setForca(-dano);
                return true;
            }
        }

        return false;
    }

    public void multiply(Area area) {
        setForca(-25);
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
                ", dano=" + dano +
                ", matingCounter=" + matingCounter +
                '}';
    }

    @Override
    public FaunaData clone() throws CloneNotSupportedException{
        return (FaunaData) super.clone();
    }
}
