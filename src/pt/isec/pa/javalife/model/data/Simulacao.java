package pt.isec.pa.javalife.model.data;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Set;

public class Simulacao implements Serializable {

    protected Ecossistema ecossistema;
    protected PropertyChangeSupport pcs;
    protected SimulacaoState state;
    // valores para a simulacao
    protected long tempoDeInstante;

    public static final String PROP_UPDATE_SIMULACAO = "_update_simulacao_";


    public Simulacao() {
        tempoDeInstante = 10;
        this.ecossistema = new Ecossistema();
        this.pcs = new PropertyChangeSupport(this);
        state = SimulacaoState.NULL;
    }


    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }
    public void addPropertyChangeListenerEcossitema(String property, PropertyChangeListener listener) {
        ecossistema.addPropertyChangeListener(property, listener);
    }

    public boolean addElemento(IElemento elem) {
        return ecossistema.addElemento(elem);
    }

    @Override
    public String toString() {
        return ecossistema.toString();
    }

    public IElemento removeElemento(int id, Elemento tipo) {
        return ecossistema.removeElemento(id, tipo);
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public double getAlturaEcossistema() { return ecossistema.getAltura(); }

    public double getLarguraEcossistema() {
        return ecossistema.getLargura();
    }

    public Ecossistema getEcossistema() { return ecossistema; }

    public SimulacaoState getState() {
        return state;
    }

    public double getDanoFauna() {
        return Ecossistema.danoFauna;
    }

    public double getRegenFlora() {
        return Ecossistema.regenFlora;
    }

    public int getTempo() {
        return ecossistema.getTempo();
    }

    public long getTempoDeInstante() {
        return tempoDeInstante;
    }

    public void setState(SimulacaoState state) {
        this.state = state;
        pcs.firePropertyChange(PROP_UPDATE_SIMULACAO, null, null);
    }

    public boolean setDanoFauna(double valorNovo) {
        return ecossistema.setDanoFauna(valorNovo);
    }
    public boolean setRegenFlora(double regen) {
        return ecossistema.setRegenFlora(regen);
    }

    public boolean setAltura(double altura) {
        ecossistema.setAltura(altura);
        return true;
    }

    public boolean setLargura(double largura) {
        ecossistema.setLargura(largura);
        return true;
    }


    public boolean setTempo(long tempo) {
        tempoDeInstante = tempo;
        return true;

    }

    public void setEventoSol() {
        ecossistema.setEventoSol();
    }


    // +----------------------------------------------------------------------------------------------------------------
    // + Opcoes +-------------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public void start(){
        ecossistema.criarCerca();
        pcs.firePropertyChange(PROP_UPDATE_SIMULACAO, null, null);
    }

    public void stop(){
        ecossistema.clear();
        pcs.firePropertyChange(PROP_UPDATE_SIMULACAO, null, null);
    }

    public void pause(){
        pcs.firePropertyChange(PROP_UPDATE_SIMULACAO, null, null);
    }

    public void resume() {
        pcs.firePropertyChange(PROP_UPDATE_SIMULACAO, null, null);
    }

    public void evAddForcaFauna(Fauna elemento) {
        ecossistema.evAddForcaFauna(elemento);
    }

    public boolean editFlora(int id, double forca, int numReproducoes) {
        return ecossistema.editFlora(id, forca, numReproducoes);
    }

    public boolean editFauna(int id, double forca, double velocidade) {
        return ecossistema.editFauna(id, forca, velocidade);
    }

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public void setPcs(PropertyChangeSupport pcs) {
        this.pcs = pcs;
    }

    public PropertyChangeSupport getEcossistemaPcs() {
        return ecossistema.getPcs();
    }

    public void setEcossistemaPcs(PropertyChangeSupport pcsEcossistema) {
        ecossistema.setPcs(pcsEcossistema);
    }

    public boolean setAreaElem(ElementoBase elem, Area valorNovo) {
        return ecossistema.setAreaElem(elem,valorNovo);
    }

    public IElemento getElementoById(int id, Elemento tipo) {
        return ecossistema.getElementoById(id,tipo);
    }
}
