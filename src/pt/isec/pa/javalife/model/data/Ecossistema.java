package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements Serializable, IGameEngineEvolve {
    private Set<IElemento> elementos;
    private int largura;
    private int altura;

    public Ecossistema(int largura, int altura) {
        elementos = new HashSet<>();
        this.largura = largura;
        this.altura = altura;
    }

    public boolean addElemento(IElemento elemento) {
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
        if(elemento instanceof Fauna){
            return removeFauna(elemento.getId());
        }else if(elemento instanceof Flora){
            return removeFlora(elemento.getId());
        }else if(elemento instanceof Inanimado){
            return removeInanimado(elemento.getId());
        }
        return null;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ecossistema: \n" + altura + "x" + largura + "\nElementos: \n");
        for(IElemento f : elementos)
            sb.append("\t---> ").append(f).append("\n");
        return sb.toString();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {}

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

}
