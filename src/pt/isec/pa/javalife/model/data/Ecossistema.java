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
    private double dano;
    private int tempo;

    public static final String PROP_UPDATE_MAP = "_update_map_";

    public Ecossistema() {
        elementos = new HashSet<>();
        pcs = new PropertyChangeSupport(this);
        this.largura = 800.0;
        this.altura = 450.0;
        dano = 1.0;
        tempo = 0;
        criarCerca();
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters +------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

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

    public void criarCerca(){
        double larguraDaBarraira = 15;
        for (int i = 0; i < 10; i++) {
            addElemento(new Inanimado(i * largura / 10, 0, (i + 1) * largura / 10, larguraDaBarraira, false));
            addElemento(new Inanimado(i * largura / 10, altura - larguraDaBarraira, (i + 1) * largura / 10, altura, false));
            addElemento(new Inanimado(0, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, larguraDaBarraira, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10, false));
            addElemento(new Inanimado(largura - larguraDaBarraira, larguraDaBarraira + i * (altura - larguraDaBarraira * 2)  / 10, largura, larguraDaBarraira + (i + 1) * (altura - larguraDaBarraira * 2) / 10, false));
        }
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public boolean addElemento(IElemento elemento) {
        Area area = elemento.getArea();

        for (IElemento elem :  elementos){
            if (elem.getType() == Elemento.INANIMADO && elem.getArea().isOverlapping(area)) {
                return false;
            }
        }

        if (elemento.getType() == Elemento.FLORA) {
            ((Flora)elemento).setEcossistema(this);
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
        tempo++;
        if (tempo == 40) {
            while(true);
        }
        Set<IElemento> copySet = new HashSet<>(elementos);
        System.out.println("Evolve");

        for(IElemento elem : copySet){
            if(elem.getType() == Elemento.FLORA){
                ((Flora)elem).move(copySet);
                if( ((Flora)elem).isDead() ){
                    elementos.remove(elem);
                }
            }

            if (elem.getType() == Elemento.FAUNA) {
                assert elem instanceof Fauna;
                ((Fauna)elem).move_context();
                if (((Fauna)elem).isDead()) {
                    elementos.remove(elem);
                }
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

    public double getDano() {
        return dano;
    }

    public boolean setDano(double valorNovo) {
        dano = valorNovo;
        for(IElemento elem : elementos){
            if(elem instanceof Flora){
                ((Flora)elem).setDano(dano);
            }
        }
        return true;
    }

    public int getTempo() {
        return tempo;
    }



}
