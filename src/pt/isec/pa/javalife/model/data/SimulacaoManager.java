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

public class SimulacaoManager implements Serializable {
    protected CommandManager commandManager;
    protected Simulacao simulacao;
    protected PropertyChangeSupport pcs;
    protected transient IMemento memento;
    protected transient IGameEngine gameEngine;

    public static final String PROP_UPDATE_COMMAND = "_update_COMMAND_";
    public static final String PROP_ADD_LIS = "UPDATE_ADD_LIS";

    /**
     * Construtor da classe SimulacaoManager.
     * Inicializa a simulação, o gerenciador de comandos e o suporte para mudanças de propriedade.
     */
    public SimulacaoManager() {
        simulacao = new Simulacao();
        this.commandManager = new CommandManager();
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(simulacao.getEcossistema());
        this.pcs = new PropertyChangeSupport(this);
        //this.pcs = new PropertyChangeSupport(this);
    }

    public void setGameEngine() {
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(simulacao.getEcossistema());
    }

    /**
     * Adiciona um listener para mudanças de propriedade.
     *
     * @param property Nome da propriedade.
     * @param listener O listener que será notificado sobre mudanças.
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
        return commandManager.invokeCommand(new AdicionaElemento(this, new Fauna(XI, YI, XF, YF, simulacao.getEcossistema())));
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
        return commandManager.invokeCommand(new AdicionaElemento(this, new Flora( XI, YI, XF, YF)));
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
        return commandManager.invokeCommand(new AdicionaElemento(this, new Inanimado(XI, YI, XF, YF, true)));
    }
    /**
     * Remove um elemento da simulação pelo seu ID e tipo.
     *
     * @param id O ID do elemento.
     * @param tipo O tipo do elemento.
     * @return true se a operação de remover foi bem-sucedida, caso contrário false.
     */
    public boolean removerElemento(int id, Elemento tipo){
        return commandManager.invokeCommand(new RemoveElemento(this, id, tipo));
    }
    /**
     * Muda a altura da simulação.
     *
     * @param altura A nova altura.
     * @return true se a operação de mudar altura foi bem-sucedida, caso contrário false.
     */
    public boolean mudarAltura(double altura){
        return commandManager.invokeCommand(new MudaAltura(this, altura));
    }
    /**
     * Muda a largura da simulação.
     *
     * @param largura A nova largura.
     * @return true se a operação de mudar largura foi bem-sucedida, caso contrário false.
     */
    public boolean mudarLargura(double largura){
        return commandManager.invokeCommand(new MudaLargura(this, largura));
    }
    /**
     * Muda o intervalo de tempo entre os ticks da simulação.
     *
     * @param tempo -> novo tempo
     * @return true se a operação de mudar o tempo foi bem-sucedida, caso contrário false.
     */
    public boolean mudarTempo(long tempo) {
        return commandManager.invokeCommand(new MudaTempo(this, tempo));
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Game Engine +--------------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    /**
     * Inicia a simulação.
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
     */
    public void stop() {
        gameEngine.stop();
        simulacao.stop();
        commandManager.clear();
    }
    /**
     * Pausa a simulação.
     */
    public void pause() {
        gameEngine.pause();
        simulacao.pause();
    }
    /**
     * Retoma a simulação.
     */
    public void resume() {
        gameEngine.resume();
        simulacao.resume();
    }
    /**
     * Obtém o estado atual do motor de jogo.
     *
     * @return O estado atual do motor de jogo.
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

    // +----------------------------------------------------------------------------------------------------------------
    // + Getters & Setters +--------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

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

    /**
     * Obtém o estado atual da simulação.
     *
     * @return O estado atual da simulação.
     */
    public SimulacaoState getState(){
        return simulacao.getState();
    }

    public int getTempo() {
        return simulacao.getTempo();
    }

    public long getTempoDeInstante(){
        return simulacao.getTempoDeInstante();
    }

    public double getDanoFauna() { return simulacao.getDanoFauna(); }

    /**
     * Define o estado da simulação.
     *
     * @param state O novo estado da simulação.
     */
    public void setState(SimulacaoState state){
        simulacao.setState(state);
    }

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

    public boolean setAltura(double altura) {
        if(gameEngine.getCurrentState() == GameEngineState.READY){
            return simulacao.setAltura(altura);

        }
        return false;
    }

    public boolean setLargura(double largura) {
        if (gameEngine.getCurrentState() == GameEngineState.READY) {
            return simulacao.setLargura(largura);
        }
        return false;
    }

    public boolean setDanoFauna(double dano) {return simulacao.setDanoFauna(dano);}

    public boolean isHerbicida() {
        return simulacao.isEventoHerbicida();
    }
    public void setEventoHerbicida(boolean herbicida) {
        simulacao.setEventoHerbicida(herbicida);
    }

    public boolean isEventoForca(){
        return simulacao.isEvForca();
    }

    public void setEventoForca(boolean evForca){
        simulacao.setEventoForca(evForca);
    }

    public void setEventoSol() {
        simulacao.setEventoSol();
    }

    // +----------------------------------------------------------------------------------------------------------------
    // + Adds & Removes +-----------------------------------------------------------------------------------------------
    // +----------------------------------------------------------------------------------------------------------------

    public boolean addElemento(IElemento elemento) {
       return simulacao.addElemento(elemento);
    }

    public IElemento removeElemento(int id, Elemento tipo) {
        return simulacao.removeElemento(id, tipo);
    }

    public void evAddForca(Fauna elemento){
        simulacao.evAddForcaFauna(elemento);
    }

    public boolean save(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(simulacao);
            oos.writeObject(commandManager);
            oos.writeObject(Flora.getNextId());
            oos.writeObject(FaunaData.getNextId());
            oos.writeObject(Inanimado.getNextId());
        } catch (NotSerializableException e) {
            System.err.println("Object not serializable: " + e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error writing SimulacaoManager: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean load(File file) {
        if(gameEngine.getCurrentState() == GameEngineState.READY) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                simulacao = (Simulacao) ois.readObject();
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

            pcs.firePropertyChange(PROP_ADD_LIS, null, null);
            return true;
        }else if(gameEngine.getCurrentState() == GameEngineState.RUNNING || gameEngine.getCurrentState() == GameEngineState.PAUSED){
            simulacao.stop();
            System.out.println("SimulacaoManager.load" + gameEngine.getCurrentState());
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                simulacao = (Simulacao) ois.readObject();
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
            pcs.firePropertyChange(PROP_ADD_LIS, null, null);
            return true;
        }else{
            return false;
        }
    }

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

    public void newSnapshot() {
        if(gameEngine.getCurrentState() != GameEngineState.READY) {
            memento = new Snapshot(simulacao);
        }
    }

    public void getSnapshot() {
        if(gameEngine.getCurrentState() != GameEngineState.READY) {
            simulacao = (Simulacao) memento.getSnapshot();
            gameEngine.registerClient(simulacao.getEcossistema());
        }
    }

}
