package pt.isec.pa.javalife.model.data;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.Commands.AdicionaElemento;
import pt.isec.pa.javalife.model.command.Commands.MudaAltura;
import pt.isec.pa.javalife.model.command.Commands.MudaLargura;
import pt.isec.pa.javalife.model.command.Commands.RemoveElemento;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

public class SimulacaoManager {
    protected CommandManager commandManager;
    protected Simulacao simulacao;
    protected PropertyChangeSupport pcs;

    public static final String PROP_UPDATE_COMMAND = "_update_COMMAND_";

    /**
     * Construtor da classe SimulacaoManager.
     * Inicializa a simulação, o gerenciador de comandos e o suporte para mudanças de propriedade.
     */
    public SimulacaoManager() {
        simulacao = new Simulacao();
        this.commandManager = new CommandManager();
        this.pcs = new PropertyChangeSupport(this);
        //this.pcs = new PropertyChangeSupport(this);
    }
    /**
     * Adiciona um listener para mudanças de propriedade.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(PROP_UPDATE_COMMAND, listener);
    }
    /*
     *  ///////////////////////////////////////////////////////////// Command
     */
    /**
     * Desfaz a última operação.
     *
     * @return true se a operação de desfazer foi bem-sucedida, caso contrário false.
     */
    public boolean undo(){
        boolean res = commandManager.undo();
        pcs.firePropertyChange(PROP_UPDATE_COMMAND, null, null);
        return res;
    }
    /**
     * Refaz a última operação desfeita.
     *
     * @return true se a operação de refazer foi bem-sucedida, caso contrário false.
     */
    public boolean redo(){
        boolean res = commandManager.redo();
        pcs.firePropertyChange(PROP_UPDATE_COMMAND, null, null);
        return res;
    }
    /**
     * Verifica se há operações que podem ser desfeitas.
     *
     * @return true se houver operações para desfazer, caso contrário false.
     */
    public boolean hasUndo(){
        return commandManager.hasUndo();
    }
    /**
     * Verifica se há operações que podem ser refeitas.
     *
     * @return true se houver operações para refazer, caso contrário false.
     */
    public boolean hasRedo(){
        return commandManager.hasRedo();
    }
    /**
     * Adiciona um elemento de fauna na simulação.
     *
     * @param XI Coordenada X inicial.
     * @param YI Coordenada Y inicial.
     * @param XF Coordenada X final.
     * @param YF Coordenada Y final.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     */
    public boolean adicionarFauna(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new FaunaContext(XI, YI, XF, YF)));
    }
    /**
     * Adiciona um elemento de flora na simulação.
     *
     * @param XI Coordenada X inicial.
     * @param YI Coordenada Y inicial.
     * @param XF Coordenada X final.
     * @param YF Coordenada Y final.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     */
    public boolean adicionarFlora(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new Flora( XI, YI, XF, YF)));
    }
    /**
     * Adiciona um elemento inanimado na simulação.
     *
     * @param XI Coordenada X inicial.
     * @param YI Coordenada Y inicial.
     * @param XF Coordenada X final.
     * @param YF Coordenada Y final.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     */
    public boolean adicionarInanimado(double XI, double YI, double XF, double YF){
        return commandManager.invokeCommand(new AdicionaElemento(simulacao, new Inanimado(XI, YI, XF, YF, true)));
    }
    /**
     * Remove um elemento da simulação pelo seu ID e tipo.
     *
     * @param id O ID do elemento.
     * @param tipo O tipo do elemento.
     * @return true se a operação de remover foi bem-sucedida, caso contrário false.
     */
    public boolean removerElemento(int id, String tipo){
        return commandManager.invokeCommand(new RemoveElemento(simulacao, id, tipo));
    }
    /**
     * Muda a altura da simulação.
     *
     * @param altura A nova altura.
     * @return true se a operação de mudar altura foi bem-sucedida, caso contrário false.
     */
    public boolean mudarAltura(double altura){
        return commandManager.invokeCommand(new MudaAltura(simulacao, altura));
    }
    /**
     * Muda a largura da simulação.
     *
     * @param largura A nova largura.
     * @return true se a operação de mudar largura foi bem-sucedida, caso contrário false.
     */
    public boolean mudarLargura(double largura){
        return commandManager.invokeCommand(new MudaLargura(simulacao, largura));
    }
    /*
     *  ///////////////////////////////////////////////////////////// GameEngine
     */
    /**
     * Inicia a simulação.
     */
    public void start() {
        if(simulacao.getCurrentState_Of_GameEngine() == GameEngineState.READY) {
            commandManager.clear();
        }

        simulacao.start();
    }
    /**
     * Para a simulação.
     */
    public void stop() {
        simulacao.stop();
        commandManager.clear();
    }
    /**
     * Pausa a simulação.
     */
    public void pause() {
        simulacao.pause();
    }
    /**
     * Retoma a simulação.
     */
    public void resume() {
        simulacao.resume();
    }
    /**
     * Obtém o estado atual do motor de jogo.
     *
     * @return O estado atual do motor de jogo.
     */
    public GameEngineState getCurrentState_Of_GameEngine() {
        return simulacao.getCurrentState_Of_GameEngine();
    }
    /*
     *  ///////////////////////////////////////////////////////////// Observer/Observable pattern
     */
    /**
     * Adiciona um listener para mudanças de propriedade na simulação.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     */
    public void addPropertyChangeListenerSimulacao(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListener(property, listener);
    }
    /**
     * Adiciona um listener para mudanças de propriedade no ecossistema.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     */
    public void addPropertyChangeListenerEcossistema(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListenerEcossitema(property, listener);
    }
    /**
     * Obtém a altura do ecossistema.
     *
     * @return A altura do ecossistema.
     */
    public double getAlturaEcossistema() {
        return simulacao.getAlturaEcossistema();
    }
    /**
     * Obtém a largura do ecossistema.
     *
     * @return A largura do ecossistema.
     */
    public double getLarguraEcossistema() {
        return simulacao.getLarguraEcossistema();
    }
    /**
     * Obtém os elementos do ecossistema.
     *
     * @return Um conjunto de elementos do ecossistema.
     */
    public Set<IElemento> getElementos() {
        return simulacao.getElementos();
    }
    /*
     *  ///////////////////////////////////////////////////////////// set
     */
    /**
     * Define o estado da simulação.
     *
     * @param state O novo estado da simulação.
     */
    public void setState(SimulacaoState state){
        simulacao.setState(state);
    }
    /**
     * Obtém o estado atual da simulação.
     *
     * @return O estado atual da simulação.
     */
    public SimulacaoState getState(){
        return simulacao.getState();
    }

}
