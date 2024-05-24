package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements Serializable, IGameEngineEvolve {
    private Set<IElemento> elementos;
    PropertyChangeSupport pcs;

    private double largura;
    private double altura;

    public static final String PROP_UPDATE_MAP = "_update_map_";

    public Ecossistema() {
        elementos = new HashSet<>();
        pcs = new PropertyChangeSupport(this);
        this.largura = 800;
        this.altura = 450;
        criarCerca();
    }

    public void criarCerca(){
        double larguraDaBarraira = 15;
        for (int i = 0; i < 10; i++) {
            addElemento(new Inanimado(i * largura / 10, 0, (i + 1) * largura / 10, larguraDaBarraira));
            addElemento(new Inanimado(i * largura / 10, altura - larguraDaBarraira, (i + 1) * largura / 10, altura));
            addElemento(new Inanimado(0, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, larguraDaBarraira, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10));
            addElemento(new Inanimado(largura - larguraDaBarraira, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, largura, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10));
        }

    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public boolean addElemento(IElemento elemento) {
        Area area = elemento.getArea();

        for (IElemento elem :  elementos){
            if(elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(area)){
                return false;
            }
        }

        return elementos.add(elemento);
    }

    public boolean addEventoHerbicida(int[] array) {
        for(IElemento f : elementos){
            if(f instanceof Flora && ArrayContains(array, f.getId())){
                ((Flora)f).addEevnto(new EventoHerbicida(f));
            }
        }
        return true;
    }

    private boolean ArrayContains(int[] array, int id) {
        for (int i : array) {
            if(i == id) return true;
        }
        return false;
    }

    public IElemento removeFauna(int id){
        for(IElemento f : elementos){
            if(f.getId() == id && f instanceof Fauna){
                elementos.remove(f);
                return f;
            }
        }
        return null;
    }

    public IElemento removeInanimado(int id) {
        for(IElemento f : elementos){
            if(f.getId() == id && f instanceof Inanimado){
                elementos.remove(f);
                return f;
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

    public IElemento removeElemento(int id, String tipo) {
        if(tipo == null) return null;
        return switch (tipo) {
            case "Fauna" -> removeFauna(id);
            case "Flora" -> removeFlora(id);
            case "Inanimado" -> removeInanimado(id);
            default -> null;
        };
    }

    public IElemento removeElemento(IElemento elemento) {
        if(elemento == null) return null;
        return switch (elemento.getType()) {
            case Elemento.FAUNA -> removeFauna(elemento.getId());
            case Elemento.FLORA -> removeFlora(elemento.getId());
            case Elemento.INANIMADO -> removeInanimado(elemento.getId());
            default -> null;
        };
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        System.out.println("Evolve");
        for(IElemento f : elementos){
            if(f instanceof Flora){
                //((Flora)f).move();
            }
        }
        pcs.firePropertyChange(PROP_UPDATE_MAP, null, null);
    }

    ////////////////////////////////////////////////////////////////////////// set
    public boolean setLargura(double largura) {
        this.largura = largura;
        return true;
    }

    public boolean setAltura(double altura) {
        this.altura = altura;
        return true;
    }

    ////////////////////////////////////////////////////////////////////////// get
    public double getAltura() { return altura; }

    public double getLargura() {
        return largura;
    }

    public Set<IElemento> getElementos() {
        return elementos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ecossistema: \n" + altura + "x" + largura + "\nElementos: \n");
        for(IElemento f : elementos)
            sb.append("\t---> ").append(f).append("\n");
        return sb.toString();
    }

    public boolean existemArvores(){
        for(IElemento f : elementos){
            if(f instanceof Flora){
                return true;
            }
        }
        return false;
    }

    public boolean existemAnimais(){
        for(IElemento f : elementos){
            if(f instanceof Fauna){
                return true;
            }
        }
        return false;
    }
}
