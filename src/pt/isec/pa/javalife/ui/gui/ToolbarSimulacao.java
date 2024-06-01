package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.ui.gui.res.MultitonImage;

public class ToolbarSimulacao extends ToolBar {

    private static final int BTN_SIZE = 40;
    private static final int BTN_IMG_SIZE = BTN_SIZE -5;

    Button btnEventoSol,btnUndo,btnRedo;

    ToggleButton btnEventoHerbicida,btnEventoForca;

    ToggleButton btnAddFlora, btnAddFauna, btnAddInanimado, btnRemoveElemento;

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
        Image imageSun = MultitonImage.getModel("sun.png");
        rectSol.setFill(new ImagePattern(imageSun));

        Rectangle rectHerbicida = new Rectangle(0,0, BTN_IMG_SIZE, BTN_IMG_SIZE);
        Image imageHerbicida = MultitonImage.getModel("herbicida.png");
        rectHerbicida.setFill(new ImagePattern(imageHerbicida));

        Rectangle rectForca = new Rectangle(0,0, BTN_IMG_SIZE, BTN_IMG_SIZE);
        Image imageInjetar = MultitonImage.getModel("injetar.png");
        rectForca.setFill(new ImagePattern(imageInjetar));



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
