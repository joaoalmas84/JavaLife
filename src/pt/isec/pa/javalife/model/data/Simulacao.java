package pt.isec.pa.javalife.model.data;

public class Simulacao {
    protected Ecossistema ecossistema;
    protected boolean iniciada;

    public Simulacao(int largura, int altura) {
        this.ecossistema = new Ecossistema(largura, altura);
        iniciada = false;
    }

    public boolean addElemento(IElemento fauna) {
        return ecossistema.addElemento(fauna);
    }

    public IElemento removeFauna(int id) {
        return ecossistema.removeFauna(id);
    }

    public IElemento removeInanimado(int id) {
        return ecossistema.removeInanimado(id);
    }

    public IElemento removeFlora(int id) {
        return ecossistema.removeFlora(id);
    }

    @Override
    public String toString() {
        return ecossistema.toString();
    }

    public int getAlturaEcossistema() {
        return ecossistema.getAltura();
    }

    public int getLarguraEcossistema() {
        return ecossistema.getLargura();
    }

    public boolean setAltura(int altura) {
        if(!iniciada){
            ecossistema.setAltura(altura);
            return true;
        }else{
            return false;
        }
    }

    public boolean setLargura(int largura) {
        if(!iniciada){
            ecossistema.setLargura(largura);
            return true;
        }else{
            return false;
        }
    }
}
