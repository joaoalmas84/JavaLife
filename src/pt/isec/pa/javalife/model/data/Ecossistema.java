package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Ecossistema implements Serializable, IGameEngineEvolve {

    private int solTicks;

    static double danoFauna = 1;

    static double forcaInjetar = 50;

    static double regenFlora=0.5;

    private Set<IElemento> elementos;
    PropertyChangeSupport pcs;

    private double largura;
    private double altura;
    private int tempo;

    public static final String PROP_UPDATE_MAP = "_update_map_";

    public Ecossistema() {
        elementos = ConcurrentHashMap.newKeySet();
        pcs = new PropertyChangeSupport(this);
        this.largura = 800.0;
        this.altura = 450.0;
        tempo = 0;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setter +---------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void clear(){
        elementos.clear();
    }

    public int getTempo() {
        return tempo;
    }

    public double getAltura() { return altura; }

    public double getLargura() { return largura; }

    public Set<IElemento> getElementos() {
        return new HashSet<>(elementos);
    }

    public Fauna getMaisForte(int id){
        Fauna fauna = null;
        double forcaMaisForte = -100;
        for(IElemento elem : elementos){
            if (elem.getType() == Elemento.FAUNA && elem.getId() != id) {
                if(((Fauna)elem).getForca() > forcaMaisForte){
                    forcaMaisForte = ((Fauna)elem).getForca();
                    fauna = (Fauna) elem;
                }
            }
        }
        return  fauna;
    }

    public Fauna getMaisFraco(int id) {
        Fauna fauna = null;
        double forcaMaisFraco = Double.MAX_VALUE;
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FAUNA && elem.getId() != id) {
                if(((Fauna)elem).getForca() < forcaMaisFraco){
                    forcaMaisFraco = ((Fauna)elem).getForca();
                    fauna = (Fauna) elem;
                }
            }
        }
        return  fauna;
    }

    public Flora getFloraMaisProxima(Area area){
        Flora floraMaisProxima = null;
        double distancia = Double.MAX_VALUE;
        for (IElemento elem : elementos) {
            if (elem.getType() == Elemento.FLORA) {
                if(elem.getArea().distanceTo(area) < distancia){
                    distancia = elem.getArea().distanceTo(area);
                    floraMaisProxima = (Flora)elem;
                }
            }
        }
        return floraMaisProxima;
    }

    public void setLargura(double largura) { this.largura = largura; }

    public void setAltura(double altura) { this.altura = altura; }

    public boolean setDanoFauna(double valorNovo) {
        if(valorNovo>0){
            danoFauna = valorNovo;
            return true;
        }
        return false;
    }
    public boolean setRegenFlora(double valorNovo) {
        if(valorNovo>0){
            regenFlora = valorNovo;
            return true;
        }
        return false;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Add +----------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property,listener);
    }

    public boolean addElemento(IElemento elemento) {
        Area area = elemento.getArea();

        if(!area.isOverlapping(new Area(0,0,largura,altura)))
            return false;

        for (IElemento elem :  elementos){
            if(elemento.getType() == Elemento.FLORA)
                if (elem.getArea().isOverlapping(area) && elem.getType() != Elemento.FAUNA)
                    return false;
            if (elemento.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(area)) {
                return false;
            }
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(area)) {
                return false;
            }
        }

        if (elemento.getType() == Elemento.FLORA) {
            ((Flora)elemento).setEcossistema(this);
        }

        return elementos.add(elemento);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Remove +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public IElemento removeFauna(int id){
        for(IElemento elem : elementos){
            if(elem.getId() == id && elem.getType() == Elemento.FAUNA){
                elementos.remove(elem);
                return elem;
            }
        }
        return null;
    }

    public IElemento removeInanimado(int id) {
        for(IElemento elem : elementos){
            if(elem.getId() == id && elem.getType() == Elemento.INANIMADO){
                if(!((Inanimado)elem).podeRemove()){ return null; }

                elementos.remove(elem);
                return elem;
            }
        }
        return null;
    }

    public IElemento removeFlora(int id) {
        for(IElemento f : elementos){
            if(f.getId() == id && f instanceof Flora){
                elementos.remove(f);
                return f;
            }
        }
        return null;
    }

    public IElemento removeElemento(int id, Elemento tipo) {
        if(tipo == null) return null;
        return switch (tipo) {
            case Elemento.FAUNA -> removeFauna(id);
            case Elemento.FLORA -> removeFlora(id);
            case Elemento.INANIMADO -> removeInanimado(id);
        };
    }
    // +----------------------------------------------------------------------------------------------------------------
    // + Evento +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void setEventoSol() {
        this.solTicks=10;
    }

    public boolean isEventoSol(){
        return(solTicks>0);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Verificacoes +-------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean existemArvores(){
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FLORA){
                return true;
            }
        }
        return false;
    }

    public boolean existemAnimais(){
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA){
                return true;
            }
        }
        return false;
    }

    public boolean existemPartners(){
        int count=0;
        for(IElemento elem : elementos){
            if(elem.getType() == Elemento.FAUNA){
                count++;
                if (count>1){
                    return true;
                }
            }
        }
        return false;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Outras +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------


    public void criarCerca(){
        double larguraDaBarraira = 15;
        for (int i = 0; i < 10; i++) {
            addElemento(new Inanimado(i * largura / 10, 0, (i + 1) * largura / 10, larguraDaBarraira, false));
            addElemento(new Inanimado(i * largura / 10, altura - larguraDaBarraira, (i + 1) * largura / 10, altura, false));
            addElemento(new Inanimado(0, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, larguraDaBarraira, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10, false));
            addElemento(new Inanimado(largura - larguraDaBarraira, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, largura, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10, false));
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        tempo++;
        System.out.println("Evolve");

        elementos.parallelStream().forEach(elem -> { //precorrer em paralelo para aumentar a pefrormance
            if (elem.getType() == Elemento.FLORA) {
                ((Flora) elem).evolve(elementos);
                if (((Flora) elem).isDead()) {
                    elementos.remove(elem);
                }
            } else if (elem.getType() == Elemento.FAUNA) {
                ((Fauna) elem).act_context();
                if (((Fauna) elem).isDead()) {
                    elementos.remove(elem);
                }
            }
        });
        solTicks--;
        pcs.firePropertyChange(PROP_UPDATE_MAP, null, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ecossistema: \n" + altura + "x" + largura + "\nElementos: \n");
        for(IElemento f : elementos)
            sb.append("\t---> ").append(f).append("\n");
        return sb.toString();
    }

    public void evAddForcaFauna(Fauna elemento) {
        elemento.addForca(forcaInjetar);
    }

    public boolean editFlora(int id, double forca, int numReproducoes) {
        Flora f =(Flora) getElementoById(id,Elemento.FLORA);
        f.addForca(forca - f.getForca());
        f.setNumReproducoes(numReproducoes);
        return true;
    }

    public boolean editFauna(int id, double forca, double velocidade) {
        Fauna f=(Fauna) getElementoById(id,Elemento.FAUNA);
        f.addForca(forca - f.getForca());
        f.setVelocidade(velocidade);
        return true;
    }

    public PropertyChangeSupport getPcs(){
        return pcs;
    }

    public void setPcs(PropertyChangeSupport pcs1){
        pcs=pcs1;
    }

    public boolean setAreaElem(ElementoBase elem, Area valorNovo) {
        boolean pode=true;
        for(IElemento elemento:elementos) {
            if(elemento.getId()==elem.getId())
                continue;
            if ((valorNovo.isOverlapping(elemento.getArea()) && ((elemento.getType() == Elemento.INANIMADO) || (elem.getType() == Elemento.FLORA && elemento.getType() == Elemento.FLORA)))) {
                pode = false;
                break;
            }
            if(elem.getType()==Elemento.INANIMADO && !((Inanimado)elem).podeRemove()){
                pode = false;
                break;
            }
        }
        if(pode){
            elem.setArea(valorNovo);
            return true;
        }
        return false;
    }

    public IElemento getElementoById(int id, Elemento tipo) {
        for(IElemento elemento:elementos){
            if(elemento.getType()==tipo && elemento.getId()==id){
                return elemento;
            }
        }
        return null;
    }
}
