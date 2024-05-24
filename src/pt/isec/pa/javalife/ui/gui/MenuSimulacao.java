package pt.isec.pa.javalife.ui.gui;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

public class MenuSimulacao extends MenuBar {
    SimulacaoManager simulacaoManager;
    Menu mnFile, mnEdit;
    MenuItem mnUndo, mnRedo, mnSave, mnAddElemento, mnPause;

    public MenuSimulacao(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnFile = new Menu("File");
        mnEdit = new Menu("Edit");

        mnUndo = new MenuItem("_Undo");
        mnRedo = new MenuItem("_Redo");
        mnSave = new MenuItem("_Save");
        mnAddElemento = new MenuItem("_Add Elemento");
        mnPause = new MenuItem("_Pause");

        mnPause.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        mnAddElemento.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        mnEdit.getItems().addAll(mnUndo, mnRedo, mnAddElemento, mnPause);

        this.getMenus().addAll(mnFile, mnEdit);

    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());

        mnAddElemento.setOnAction(e -> {
            simulacaoManager.setState(SimulacaoState.ADD);
            simulacaoManager.pause();
        });

        mnPause.setOnAction(e -> {
            if(simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.PAUSED){
                simulacaoManager.resume();
            }else {
                simulacaoManager.pause();
            }
        });
    }

    private void update() {
        switch (simulacaoManager.getCurrentState_Of_GameEngine())
        {
            case GameEngineState.PAUSED:
                mnPause.setVisible(true);
                mnAddElemento.setVisible(true);
                mnPause.setText("_Resume");
                break;
            case GameEngineState.RUNNING:
                mnPause.setVisible(true);
                mnAddElemento.setVisible(true);
                mnPause.setText("_Pause");
                break;
            case  GameEngineState.READY:
                mnPause.setVisible(false);
                mnAddElemento.setVisible(false);
                break;
        }
    }

}
