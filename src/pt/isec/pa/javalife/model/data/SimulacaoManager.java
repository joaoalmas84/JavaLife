package pt.isec.pa.javalife.model.data;

import javafx.application.Platform;
import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.commands.*;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.memento.IMemento;
import pt.isec.pa.javalife.model.memento.Snapshot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Set;
/**
 * Esta classe representa um exemplo de uso de tags Javadoc.
 *
 * @author Guilherme Lopes / João Pinto / João Almas
 * @version 1.0
 * @since 2024-05-30
 */
public class SimulacaoManager implements Serializable {
    /**
     * Gerenciador de comandos da simulação.
     */
    protected CommandManager commandManager;
    /**
     * Simulação associada ao gerenciador.
     */
    protected Simulacao simulacao;
    /**
     * Simulação associada ao gerenciador.
     */
    protected PropertyChangeSupport pcs;
    /**
     * Interface para captura de instantâneos da simulação.
     */
    protected transient IMemento memento;
    /**
     * Motor do jogo responsável pela execução da simulação.
     */
    protected transient IGameEngine gameEngine;
    /**
     * Propriedade para atualização de comandos.
     */
    public static final String PROP_UPDATE_COMMAND = "_update_COMMAND_";
    /**
     * Construtor da classe SimulacaoManager.
     * Inicializa a simulação, o gerenciador de comandos e o suporte para mudanças de propriedade.
     * @since 1.0
     *
     */
    public SimulacaoManager() {
        simulacao = new Simulacao();
        this.commandManager = new CommandManager();
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(simulacao.getEcossistema());
        this.pcs = new PropertyChangeSupport(this);
        //this.pcs = new PropertyChangeSupport(this);
    }
    /**
     * Adiciona um listener para mudanças de propriedade.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     * @since 1.0
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(PROP_UPDATE_COMMAND, listener);
        pcs.addPropertyChangeListener(property, listener);
    }
    // +----------------------------------------------------------------------------------------------------------------
    // + Commands +-----------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------
    /**
     * Desfaz a última operação.
     *
     * @return true se a operação de desfazer foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean undo(){
        boolean res = commandManager.undo();
        pcs.firePropertyChange(PROP_UPDATE_COMMAND, null, null);
        return res;
    }
    /**
     * Refaz a última operação desfeita.
     *
     * @return true se a operação de refazer foi bem-sucedida, caso contrário false.7
     * @since 1.0
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
     * @since 1.0
     */
    public boolean hasUndo(){
        return commandManager.hasUndo();
    }
    /**
     * Verifica se há operações que podem ser refeitas.
     *
     * @return true se houver operações para refazer, caso contrário false.
     * @since 1.0
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
     * @since 1.0
     */
    public boolean adicionarFauna(double XI, double YI, double XF, double YF){
        boolean res= commandManager.invokeCommand(new AdicionaElemento(this, new Fauna(XI, YI, XF, YF, simulacao.getEcossistema())));
        if (res){
            pcs.firePropertyChange(PROP_UPDATE_COMMAND,null,null);
        }
        return res;
    }
    /**
     * Adiciona um elemento de flora na simulação.
     *
     * @param XI Coordenada X inicial.
     * @param YI Coordenada Y inicial.
     * @param XF Coordenada X final.
     * @param YF Coordenada Y final.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean adicionarFlora(double XI, double YI, double XF, double YF){
        boolean res= commandManager.invokeCommand(new AdicionaElemento(this, new Flora( XI, YI, XF, YF)));
        if (res){
            pcs.firePropertyChange(PROP_UPDATE_COMMAND,null,null);
        }
        return res;
    }
    /**
     * Adiciona um elemento inanimado na simulação.
     *
     * @param XI Coordenada X inicial.
     * @param YI Coordenada Y inicial.
     * @param XF Coordenada X final.
     * @param YF Coordenada Y final.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean adicionarInanimado(double XI, double YI, double XF, double YF){
        boolean res= commandManager.invokeCommand(new AdicionaElemento(this, new Inanimado(XI, YI, XF, YF, true)));
        if (res){
            pcs.firePropertyChange(PROP_UPDATE_COMMAND,null,null);
        }
        return res;
    }
    /**
     * Remove um elemento da simulação pelo seu ID e tipo.
     *
     * @param id O ID do elemento.
     * @param tipo O tipo do elemento.
     * @return true se a operação de remover foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean removerElemento(int id, Elemento tipo){
        boolean res= commandManager.invokeCommand(new RemoveElemento(this, id, tipo));
        if (res){
            pcs.firePropertyChange(PROP_UPDATE_COMMAND,null,null);
        }
        return res;
    }
    /**
     * Muda a altura do elemento.
     *
     * @param id o id do elemento.
     * @param tipo tipo do elemento.
     * @param altura altura nova do elemento
     * @return true se a operação de mudar altura foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean mudarAlturaElem(int id,Elemento tipo,double altura){
        ElementoBase elemento=(ElementoBase) getElementoById(id,tipo);
        if(elemento!=null){
            boolean res = commandManager.invokeCommand(new MudaAlturaElem(this, elemento, altura));
            if (res) {
                pcs.firePropertyChange(PROP_UPDATE_COMMAND, null, null);
            }
            return res;
        }
        return false;
    }
    /**
     * Muda a largura do elemento.
     *
     * @param id o id do elemento.
     * @param tipo tipo do elemento.
     * @param largura largura nova do elemento
     * @return true se a operação de mudar largura foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean mudarLarguraElem(int id,Elemento tipo,double largura){
        ElementoBase elemento=(ElementoBase) getElementoById(id,tipo);
        if(elemento!=null){
            boolean res = commandManager.invokeCommand(new MudaLarguraElem(this, elemento, largura));
            if (res) {
                pcs.firePropertyChange(PROP_UPDATE_COMMAND, null, null);
            }
            return res;
        }
        return false;
    }
    /**
     * Muda o intervalo de tempo entre os ticks da simulação.
     *
     * @param tempo -> novo tempo
     * @return true se a operação de mudar o tempo foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean mudarTempo(long tempo) {
        boolean res= commandManager.invokeCommand(new MudaTempo(this, tempo));
        if (res){
            pcs.firePropertyChange(PROP_UPDATE_COMMAND,null,null);
        }
        return res;
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Game Engine +--------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    /**
     * Inicia a simulação.
     * @since 1.0
     */
    public void start() {
        if(gameEngine.getCurrentState() == GameEngineState.READY) {
            commandManager.clear();
        }

        gameEngine.start(simulacao.getTempoDeInstante());
        simulacao.start();
    }
    /**
     * Para a simulação.
     * @since 1.0
     */
    public void stop() {
        gameEngine.stop();
        simulacao.stop();
        commandManager.clear();
    }
    /**
     * Pausa a simulação.
     * @since 1.0
     */
    public void pause() {
        gameEngine.pause();
        simulacao.pause();
    }
    /**
     * Retoma a simulação.
     * @since 1.0
     */
    public void resume() {
        gameEngine.resume();
        simulacao.resume();
    }
    /**
     * Obtém o estado atual do motor de jogo.
     *
     * @return O estado atual do motor de jogo.
     * @since 1.0
     */
    public GameEngineState getCurrentState_Of_GameEngine() {
        return gameEngine.getCurrentState();
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Observer +-----------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    /**
     * Adiciona um listener para mudanças de propriedade na simulação.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     * @since 1.0
     */
    public void addPropertyChangeListenerSimulacao(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListener(property, listener);
    }
    /**
     * Adiciona um listener para mudanças de propriedade no ecossistema.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
     * @since 1.0
     */
    public void addPropertyChangeListenerEcossistema(String property, PropertyChangeListener listener) {
        simulacao.addPropertyChangeListenerEcossitema(property, listener);
    }
    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------
    /**
     * Obtém a altura do ecossistema.
     *
     * @return A altura do ecossistema.
     * @since 1.0
     */
    public double getAlturaEcossistema() {
        return simulacao.getAlturaEcossistema();
    }
    /**
     * Obtém a largura do ecossistema.
     *
     * @return A largura do ecossistema.
     * @since 1.0
     */
    public double getLarguraEcossistema() {
        return simulacao.getLarguraEcossistema();
    }
    /**
     * Obtém os elementos do ecossistema.
     *
     * @return Um conjunto de elementos do ecossistema.
     * @since 1.0
     */
    public Set<IElemento> getElementos() {
        return simulacao.getElementos();
    }
    /**
     * Obtém o elemento descrito pelo id e tipo fornecidos.
     *
     * @param id O id do elemento.
     * @param tipo O tipo do elemento.
     * @return O elemento.
     * @since 1.0
     */
    public IElemento getElementoById(int id,Elemento tipo){
        return simulacao.getElementoById(id,tipo);
    }

    /**
     * Obtém o estado atual da simulação.
     *
     * @return O estado atual da simulação.
     * @since 1.0
     */
    public SimulacaoState getState(){
        return simulacao.getState();
    }
    /**
     * Obtém o tempo da simulação.
     * @since 1.0
     * @return O tempo da simulação.
     */
    public int getTempo() {return simulacao.getTempo();}
    /**
     * Obtém o intervalo de cada tick da simulação.
     * @since 1.0
     * @return O intervalo de cada tick.
     */
    public long getTempoDeInstante(){return simulacao.getTempoDeInstante();}
    /**
     * Obtém o dano da fauna.
     * @since 1.0
     * @return O dano da fauna.
     */
    public double getDanoFauna() { return simulacao.getDanoFauna(); }
    /**
     * Obtém a regeneração da flora.
     * @since 1.0
     * @return A regeneração da flora.
     */
    public double getRegenFlora() { return simulacao.getRegenFlora(); }
    /**
     * Define o estado da simulação.
     *
     * @param state O novo estado da simulação.
     */
    public void setState(SimulacaoState state){
        simulacao.setState(state);
    }
    /**
     * Define o tempo do intervalo de cada tick da simulação.
     *
     * @param tempo tempo de espaco de cada tick.
     * @return true se a operação de mudar o tempo foi bem-sucedida, caso contrário false.
     */
    public boolean setTempo(long tempo) {
        if (gameEngine.getCurrentState() == GameEngineState.READY) {
            return false ;
        }
        if (tempo >= 10) {
            gameEngine.setInterval(tempo);
            return simulacao.setTempo(tempo);
        } else {
            return false;
        }
    }
    /**
     * Define a altura do ecossistema.
     *
     * @param altura A nova altura do ecossistema.
     * @return true se a operação de mudar a altura foi bem-sucedida, caso contrário false.
     */
    public boolean setAltura(double altura) {
        if(gameEngine.getCurrentState() == GameEngineState.READY){
            return simulacao.setAltura(altura);

        }
        return false;
    }
    /**
     * Define a largura do ecossistema.
     *
     * @param largura A nova largura do ecossistema.
     * @return true se a operação de mudar a largura foi bem-sucedida, caso contrário false.
     */
    public boolean setLargura(double largura) {
        if (gameEngine.getCurrentState() == GameEngineState.READY) {
            return simulacao.setLargura(largura);
        }
        return false;
    }
    /**
     * Define o dano da fauna.
     *
     * @param dano O novo dano da fauna.
     * @return true se a operação de mudar o dano da fauna foi bem-sucedida, caso contrário false.
     */
    public boolean setDanoFauna(double dano) {return simulacao.setDanoFauna(dano);}
    /**
     * Define a regenseração da flora.
     *
     * @param regen A nova taxa de regeneração da flora.
     * @return true se a operação de mudar a regeneração da flora foi bem-sucedida, caso contrário false.
     */
    public boolean setRegenFlora(double regen) {return simulacao.setRegenFlora(regen);}
    /**
     * Define o evento de sol.
     * @since 1.0
     */
    public void setEventoSol() {
        simulacao.setEventoSol();
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Adds & Removes +-----------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------
    /**
     * Adiciona um elemento à simulação.
     *
     * @param elemento O elemento a ser adicionado.
     * @return true se a operação de adicionar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean addElemento(IElemento elemento) {
       return simulacao.addElemento(elemento);
    }
    /**
     * Remove um elemento da simulação.
     *
     * @param id O ID do elemento a ser removido.
     * @param tipo O tipo do elemento a ser removido.
     * @return O elemento removido.
     * @since 1.0
     */
    public IElemento removeElemento(int id, Elemento tipo) {
        return simulacao.removeElemento(id, tipo);
    }
    /**
     * nao sei para que isto serve mudar depois
     *
     * @param elemento nao sei mudar depois teste
     * @since 1.0
     */
    public void evAddForca(Fauna elemento){
        simulacao.evAddForcaFauna(elemento);
    }
    /**
     * Salve em um arquivo em bits o estado da simulação do CommandManager e de alguns atributos estáticos.
     * @param file arquivo onde será salvo.
     * @return false se nao der certo e true se der certo.
     *
     * @since 1.0
     */
    public boolean save(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(simulacao);
            oos.writeObject(commandManager);
            oos.writeObject(Flora.getNextId());
            oos.writeObject(FaunaData.getNextId());
            oos.writeObject(Inanimado.getNextId());
        } catch (NotSerializableException e) {
            System.err.println("Object not serializable: " + e.getMessage());
            Platform.exit();
            return false;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            Platform.exit();
            return false;
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            Platform.exit();
            return false;
        } catch (Exception e) {
            System.err.println("Error writing SimulacaoManager: " + e.getMessage());
            Platform.exit();
            return false;
        }
        return true;
    }
    /**
     * volta de um arquivo em bits o estado da simulação do CommandManager e de alguns atributos estáticos.
     * @param file arquivo onde será lido.
     * @return false se nao der certo e true se der certo.
     * @since 1.0
     */
    public Boolean load(File file) {
        if(gameEngine.getCurrentState() == GameEngineState.RUNNING || gameEngine.getCurrentState() == GameEngineState.PAUSED){
            simulacao.stop();
            System.out.println("SimulacaoManager.load" + gameEngine.getCurrentState());
        }
        if(gameEngine.getCurrentState()==GameEngineState.READY || gameEngine.getCurrentState()==GameEngineState.RUNNING || gameEngine.getCurrentState()==GameEngineState.PAUSED){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                gameEngine.unregisterClient(simulacao.getEcossistema());

                PropertyChangeSupport pcsSimulacao = simulacao.getPcs();
                PropertyChangeSupport pcsEcossistema = simulacao.getEcossistemaPcs();
                simulacao = (Simulacao) ois.readObject();
                simulacao.setPcs(pcsSimulacao);
                simulacao.setEcossistemaPcs(pcsEcossistema);

                gameEngine.registerClient(simulacao.getEcossistema());
                commandManager = (CommandManager) ois.readObject();
                Flora.setNextId((int) ois.readObject());
                FaunaData.setNextId((int) ois.readObject());
                Inanimado.setNextId((int) ois.readObject());
            } catch (Exception e) {
                System.err.println("Error loading SimulacaoManager: " + e.getMessage());
                Platform.exit();
                return false;
            }
            return true;
        }
        else
            return false;
    }
    /**
     * Salve em um arquivo CSV o tipo, a posição e a força dos elementos da ecossistema.
     * @param file arquivo onde será salvo.
     * @return false se nao der certo e true se der certo.
     * @since 1.0
     */
    public boolean saveElementos(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            // Adicionando cabeçalho ao CSV
            writer.append("Tipo;Xi;Yi;Xf;Yf;Força\n");

            for (IElemento e : simulacao.getElementos()) {
                if(e.getType() == Elemento.FLORA || e.getType() == Elemento.FAUNA) {
                    Area area = e.getArea();
                    String tipo = e.getType().toString();
                    String linha = String.format("%s;%s;%s;%s;%s;%s",
                            tipo, area.xi(), area.yi(), area.xf(), area.yf(), ((IElementoComForca)e).getForca());
                    writer.append(linha).append("\n");
                }else if(e.getType() == Elemento.INANIMADO){
                    if(((Inanimado)e).podeRemove()){
                        Area area = e.getArea();
                        String tipo = e.getType().toString();
                        String linha = String.format("%s;%s;%s;%s;%s",
                                tipo, area.xi(), area.yi(), area.xf(), area.yf());
                        writer.append(linha).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + file);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * retorne de um arquivo CSV os elementos que estão escritos, o tipo, a força e a sua posição.
     * @param file arquivo onde será lido.
     * @since 1.0
     */
    public void loadElementosFromCSV(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Ignora o cabeçalho

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                String tipo = parts[0];
                double xi = Double.parseDouble(parts[1]);
                double yi = Double.parseDouble(parts[2]);
                double xf = Double.parseDouble(parts[3]);
                double yf = Double.parseDouble(parts[4]);

                switch (tipo) {
                    case "FLORA":
                        if (parts.length >= 6) {
                            double forca = Double.parseDouble(parts[5]);
                            Flora flora = new Flora(xi, yi, xf, yf);
                            flora.addForca(forca - flora.getForca());
                            commandManager.invokeCommand(new AdicionaElemento(this, flora));
                        } else {
                            System.err.println("Força não fornecida para o tipo FLORA.");
                        }
                        break;
                    case "FAUNA":
                        if (parts.length >= 6) {
                            double forca = Double.parseDouble(parts[5]);
                            Fauna fauna = new Fauna(xi, yi, xf, yf, simulacao.getEcossistema());
                            fauna.addForca(forca - fauna.getForca());
                            commandManager.invokeCommand(new AdicionaElemento(this, fauna));
                        } else {
                            System.err.println("Força não fornecida para o tipo FAUNA.");
                        }
                        break;
                    case "INANIMADO":
                        commandManager.invokeCommand(new AdicionaElemento(this, new Inanimado(xi, yi, xf, yf, true)));
                        break;
                    default:
                        System.err.println("Tipo desconhecido: " + tipo);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + file);
        }
    }
    /**
     * Cria um novo instantâneo da simulação.
     * @since 1.0
     */
    public void newSnapshot() {
        if(gameEngine.getCurrentState() != GameEngineState.READY) {
            memento = new Snapshot(simulacao);
        }
    }
    /**
     * Obtém o instantâneo da simulação.
     * @since 1.0
     */
    public void getSnapshot() {
        if(gameEngine.getCurrentState() != GameEngineState.READY && memento!=null) {
            gameEngine.unregisterClient(simulacao.getEcossistema());
            PropertyChangeSupport pcsSimulacao = simulacao.getPcs();
            PropertyChangeSupport pcsEcossistema = simulacao.getEcossistemaPcs();
            simulacao = (Simulacao) memento.getSnapshot();
            simulacao.setPcs(pcsSimulacao);
            simulacao.setEcossistemaPcs(pcsEcossistema);
            gameEngine.registerClient(simulacao.getEcossistema());
            commandManager.clear();
        }
    }

    /**
     * Edita um elemento de flora.
     *
     * @param id O ID do elemento.
     * @param xi Coordenada X inicial.
     * @param yi Coordenada Y inicial.
     * @param forca A força do elemento.
     * @param numReproducoes O número de reproduções do elemento.
     * @return true se a operação de editar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean editFlora(int id, double xi, double yi, double forca, int numReproducoes){
        return simulacao.editFlora(id, xi, yi, forca, numReproducoes);
    }
    /**
     * Edita um elemento de fauna.
     *
     * @param id O ID do elemento.
     * @param xi Coordenada X inicial.
     * @param yi Coordenada Y inicial.
     * @param forca A força do elemento.
     * @param velocidade A velocidade do elemento.
     * @return true se a operação de editar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean editFauna(int id, double xi, double yi, double forca, double velocidade){
        return simulacao.editFauna(id, xi, yi, forca, velocidade);
    }
    /**
     * Edita um elemento inanimado.
     *
     * @param id O ID do elemento.
     * @param xi Coordenada X inicial.
     * @param yi Coordenada Y inicial.
     * @return true se a operação de editar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean editInanimado(int id, double xi, double yi){
        return simulacao.editInanimado(id, xi, yi);
    }


    /**
     * Edita a area do elemento.
     *
     * @param elem O elemento que se pretende editar.
     * @param valorNovo A nova area do elemento.
     * @return true se a operação de editar foi bem-sucedida, caso contrário false.
     * @since 1.0
     */
    public boolean setAreaElem(ElementoBase elem, Area valorNovo) {
        return simulacao.setAreaElem(elem,valorNovo);
    }
}
