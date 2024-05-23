package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.Commands.AdicionaElemento;
import pt.isec.pa.javalife.model.command.Commands.MudaAltura;
import pt.isec.pa.javalife.model.command.Commands.MudaLargura;
import pt.isec.pa.javalife.model.command.Commands.RemoveElemento;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

import java.beans.PropertyChangeListener;
import java.util.Set;

public class SimulacaoManager {
    protected CommandManager commandManager;
    protected Simulacao simulacao;

    public SimulacaoManager() {
        simulacao = new Simulacao();
        this.commandManager = new CommandManager();
        //this.pcs = new PropertyChangeSupport(this);
    }

    //public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
    //    pcs.addPropertyChangeListener(listener);
    //}

    /**
     *  ///////////////////////////////////////////////////////////// Command
     */

    public boolean undo(){
        return commandManager.undo();
    }

    public boolean redo(){
        return commandManager.redo();
    }

    public boolean hasUndo(){
        return commandManager.hasUndo();
    }

    public boolean hasRedo(){
        return commandManager.hasRedo();
    }

    public boolean adicionarFauna(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new Fauna()));
    }

    public boolean adicionarFlora(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new Flora( XI, YI, XF, YF)));
    }

    public boolean adicionarInanimado(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new Inanimado(XI, YI, XF, YF)));
    }

    public boolean removerElemento(int id, String tipo){
        return commandManager.invokeCommand(new RemoveElemento(simulacao, id, tipo));
    }

    public boolean removerElemento(IElemento elem){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, simulacao.removeElemento(elem)));
    }

    public boolean mudarAltura(double altura){
        return commandManager.invokeCommand(new MudaAltura(simulacao, altura));
    }

    public boolean mudarLargura(double largura){
        return commandManager.invokeCommand(new MudaLargura(simulacao, largura));
    }

    /**
     *  ///////////////////////////////////////////////////////////// GameEngine
     */

    public void start() {
        if(simulacao.getCurrentState_Of_GameEngine() == GameEngineState.READY) {
            commandManager.clear();
        }

        simulacao.start();
    }

    public void stop() {
        simulacao.stop();
        commandManager.clear();
    }

    public void pause() {
        simulacao.pause();
    }

    public void resume() {
        simulacao.resume();
    }

    public GameEngineState getCurrentState_Of_GameEngine() {
        return simulacao.getCurrentState_Of_GameEngine();
    }

    /**
     *  ///////////////////////////////////////////////////////////// Observer/Observable pattern
     */

    public void addPropertyChangeListenerSimulacao(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListener(property, listener);
    }

    public void addPropertyChangeListenerEcossistema(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListenerEcossitema(property, listener);
    }

    public double getAlturaEcossistema() {
        return simulacao.getAlturaEcossistema();
    }

    public double getLarguraEcossistema() {
        return simulacao.getLarguraEcossistema();
    }

    public Set<IElemento> getElementos() {
        return simulacao.getElementos();
    }
    /**
     *  ///////////////////////////////////////////////////////////// set
     */

    public void setState(SimulacaoState state){
        simulacao.setState(state);
    }

    public SimulacaoState getState(){
        return simulacao.getState();
    }

}
