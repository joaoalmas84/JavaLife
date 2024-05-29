package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class ToolbarSimulacao extends ToolBar {

    private static final int BTN_SIZE = 40;
    private static final int BTN_IMG_SIZE = BTN_SIZE -10;

    Button btnEventoSol,btnUndo,btnRedo;

    ToggleButton btnEventoHerbicida;
    ToggleButton btnEventoForca;

    ToggleGroup tgEventos;


    SimulacaoManager manager;
    ToolbarSimulacao(SimulacaoManager simulacaoManager){
        manager=simulacaoManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Rectangle rectSol = new Rectangle(0,0, BTN_IMG_SIZE, BTN_IMG_SIZE);
        rectSol.setFill(Color.YELLOW);

        Rectangle rectHerbicida = new Rectangle(0,0, BTN_IMG_SIZE, BTN_IMG_SIZE);
        rectHerbicida.setFill(Color.BLACK);

        Rectangle rectForca = new Rectangle(0,0, BTN_IMG_SIZE, BTN_IMG_SIZE);
        rectForca.setFill(Color.RED.brighter());


        btnEventoSol = new Button(null,rectSol);
        btnEventoSol.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnEventoHerbicida=new ToggleButton(null,rectHerbicida);
        btnEventoHerbicida.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnEventoForca =new ToggleButton(null,rectForca);
        btnEventoForca.setPrefSize(BTN_SIZE,BTN_SIZE);

        tgEventos =new ToggleGroup();
        btnEventoHerbicida.setToggleGroup(tgEventos);
        btnEventoForca.setToggleGroup(tgEventos);

        btnUndo= new Button("Undo");
        btnRedo=new Button("Redo");

        getItems().addAll(btnUndo,btnRedo,new Separator(),btnEventoSol,btnEventoHerbicida,btnEventoForca);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
        manager.addPropertyChangeListener(SimulacaoManager.PROP_UPDATE_COMMAND, evt -> Platform.runLater(this::update));

        btnUndo.setOnAction(evt->manager.undo());
        btnRedo.setOnAction(evt->manager.redo());
        btnEventoSol.setOnAction(evt ->{
            manager.setEventoSol();
        });
        btnEventoHerbicida.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                manager.setEventoHerbicida(true);
            }
            else
                manager.setEventoHerbicida(false);
        });

        btnEventoForca.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                manager.setEventoForca(true);
            }
            else
                manager.setEventoForca(false);
        });

    }

    private void update() {
        btnUndo.setDisable(!manager.hasUndo());
        btnRedo.setDisable(!manager.hasRedo());
    }
}
