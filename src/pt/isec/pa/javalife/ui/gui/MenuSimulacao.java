package pt.isec.pa.javalife.ui.gui;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

import java.io.File;

public class MenuSimulacao extends MenuBar {
    SimulacaoManager simulacaoManager;
    Menu mnFile, mnEdit;
    MenuItem mnUndo, mnRedo, mnSave, mnAddElemento, mnPause, mnRemove, mnExit, mnIntervalo, mnOpen;

    public MenuSimulacao(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnFile = new Menu("File");
        mnEdit = new Menu("Propriedades");

        mnUndo = new MenuItem("_Undo");
        mnRedo = new MenuItem("_Redo");
        mnSave = new MenuItem("_Save");
        mnAddElemento = new MenuItem("_Add Elemento");
        mnPause = new MenuItem("_Pause");
        mnRemove = new MenuItem("_Remove");
        mnExit = new MenuItem("_Exit");
        mnIntervalo = new MenuItem("_Intervalo");
        mnOpen = new MenuItem("_Open");

        mnUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        mnRedo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        mnPause.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        mnAddElemento.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        mnRemove.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        mnExit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        mnIntervalo.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));

        mnEdit.getItems().addAll(mnUndo, mnRedo, mnAddElemento, mnRemove, mnPause, mnIntervalo, mnExit);
        mnFile.getItems().addAll(mnSave, mnOpen);
        this.getMenus().addAll(mnFile, mnEdit);

    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_ADD_LIS, evt -> Platform.runLater(this::setProp));
        setProp();
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_UPDATE_COMMAND, evt -> Platform.runLater(this::update));

        mnSave.setOnAction(e -> {
            if(simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.RUNNING){
                simulacaoManager.pause();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File save...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Drawing (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) {
                simulacaoManager.save(hFile);
            }
        });

        mnOpen.setOnAction(e -> {
            if(simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.RUNNING){
                simulacaoManager.pause();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Drawing (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (hFile != null) {
                simulacaoManager.load(hFile);
            }
        });


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

        mnIntervalo.setOnAction(e -> {
            simulacaoManager.pause();
            simulacaoManager.setState(SimulacaoState.INTERVALO);
        });

        mnUndo.setOnAction(e -> simulacaoManager.undo());

        mnRedo.setOnAction(e -> simulacaoManager.redo());

        mnRemove.setOnAction(e -> {
            simulacaoManager.setState(SimulacaoState.REMOVE);
            simulacaoManager.pause();
        });

        mnExit.setOnAction(e -> Platform.exit());
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }

    private void update() {
        switch (simulacaoManager.getCurrentState_Of_GameEngine())
        {
            case GameEngineState.PAUSED:
                mnPause.setVisible(true);
                mnAddElemento.setVisible(true);
                mnPause.setText("_Resume");
                mnUndo.disableProperty().setValue(!simulacaoManager.hasUndo());
                mnRedo.disableProperty().setValue(!simulacaoManager.hasRedo());
                break;
            case GameEngineState.RUNNING:
                mnPause.setVisible(true);
                mnAddElemento.setVisible(true);
                mnPause.setText("_Pause");
                mnUndo.disableProperty().setValue(!simulacaoManager.hasUndo());
                mnRedo.disableProperty().setValue(!simulacaoManager.hasRedo());
                break;
            case  GameEngineState.READY:
                mnPause.setVisible(false);
                mnAddElemento.setVisible(false);
                mnUndo.disableProperty().setValue(!simulacaoManager.hasUndo());
                mnRedo.disableProperty().setValue(!simulacaoManager.hasRedo());
                break;
        }
    }
}
