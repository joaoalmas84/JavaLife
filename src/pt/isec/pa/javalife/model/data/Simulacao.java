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
        //ecossistema.addElemento(new Fauna(130, 130, 160, 160, ecossistema));
        //ecossistema.addElemento(new Fauna(130, 130, 160, 160, ecossistema));

        ecossistema.addElemento(new Flora(15, 15, 30, 30));
        //ecossistema.addElemento(new Flora(15, 200, 30, 230));
        //ecossistema.addElemento(new Flora(15, 115, 30, 130));
        //ecossistema.addElemento(new Flora(215, 15, 230, 30));
        //ecossistema.addElemento(new Flora(215, 215, 230, 230));

    }


    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }
    public void addPropertyChangeListenerEcossitema(String property, PropertyChangeListener listener) {
        ecossistema.addPropertyChangeListener(property, listener);
    }

    public boolean addElemento(IElemento fauna) {
        return ecossistema.addElemento(fauna);
    }

    @Override
    public String toString() {
        return ecossistema.toString();
    }

    public IElemento removeElemento(int id, Elemento tipo) {
        return ecossistema.removeElemento(id, tipo);
    }

    public IElemento removeElemento(IElemento elem) {
        return ecossistema.removeElemento(elem);
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

    public boolean isEventoHerbicida() {
        return ecossistema.isEventoHerbicida();
    }

    public boolean isEvForca(){
        return ecossistema.isEvForca();
    }

    public void setEventoHerbicida(boolean herbicida) {
        ecossistema.setEventoHerbicida(herbicida);
    }

    public void setEventoForca(boolean evForca) {
        ecossistema.setEventoForca(evForca);
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



/*
    public boolean adicionaFauna(double xi, double yi, double xf, double yf){
        //return commandManager.invokeCommand(new AdicionaElemento(ecossistema ,new Fauna(xi, yi, xf, yf)));
        return false;
    }

    public boolean adicionaFlora(double xi, double yi, double xf, double yf){
        return ecossistema.addElemento(new Flora(xi, yi, xf, yf));
    }

    public boolean adicionaInanimado(double xi, double yi, double xf, double yf){
        return ecossistema.addElemento(new Inanimado(xi, yi, xf, yf, true));
    }

    public boolean removeFauna(int id){
        return removeFauna(id);
    }

    public boolean removeFlora(int id){
        return removeFlora(id);
    }

    public boolean removeInanimado(int id){
        return removeInanimado(id);
    }

*/
}
