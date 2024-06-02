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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ToolbarSimulacao extends ToolBar {

    public static final String PROP_TOGGLES="_update_toggles_";

    PropertyChangeSupport pcs;

    private static final int BTN_SIZE = 30;
    private static final int BTN_IMG_SIZE = BTN_SIZE -5;

    Button btnEventoSol,btnUndo,btnRedo;

    ToggleButton btnEventoHerbicida,btnEventoForca;

    ToggleButton btnAddFlora, btnAddFauna, btnAddInanimado, btnRemoveElemento;

    ToggleGroup tgEventos;

    SimulacaoManager manager;
    ToolbarSimulacao(SimulacaoManager simulacaoManager){
        manager=simulacaoManager;

        pcs= new PropertyChangeSupport(this);

        createViews();
        registerHandlers();
        update();
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property,listener);
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

        Rectangle rectAddFauna = new Rectangle(0,0,BTN_IMG_SIZE,BTN_IMG_SIZE);
        Image imageFauna = MultitonImage.getModel("guepardo.png");
        rectAddFauna.setFill(new ImagePattern(imageFauna));

        Rectangle rectAddFlora= new Rectangle(0,0,BTN_IMG_SIZE,BTN_IMG_SIZE);
        rectAddFlora.setFill(Color.GREEN);
        rectAddFlora.setStroke(Color.GREEN.darker());
        rectAddFlora.setStrokeWidth(3);

        Rectangle rectAddInanimado = new Rectangle(0,0,BTN_IMG_SIZE,BTN_IMG_SIZE);
        rectAddInanimado.setFill(Color.GRAY);
        rectAddInanimado.setStroke(Color.GRAY.darker());
        rectAddInanimado.setStrokeWidth(3);

        Rectangle rectRemoveElemento = new Rectangle(0,0,BTN_IMG_SIZE,BTN_IMG_SIZE);
        Image imageRemove = MultitonImage.getModel("x.png");
        rectRemoveElemento.setFill(new ImagePattern(imageRemove));



        btnEventoSol = new Button(null,rectSol);
        btnEventoSol.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnEventoHerbicida=new ToggleButton(null,rectHerbicida);
        btnEventoHerbicida.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnEventoForca =new ToggleButton(null,rectForca);
        btnEventoForca.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnAddFauna = new ToggleButton(null,rectAddFauna);
        btnAddFauna.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnAddFlora=new ToggleButton(null,rectAddFlora);
        btnAddFlora.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnAddInanimado = new ToggleButton(null,rectAddInanimado);
        btnAddInanimado.setPrefSize(BTN_SIZE,BTN_SIZE);

        btnRemoveElemento= new ToggleButton(null,rectRemoveElemento);
        btnRemoveElemento.setPrefSize(BTN_SIZE,BTN_SIZE);



        tgEventos =new ToggleGroup();
        btnEventoHerbicida.setToggleGroup(tgEventos);
        btnEventoForca.setToggleGroup(tgEventos);
        btnAddFauna.setToggleGroup(tgEventos);
        btnAddFlora.setToggleGroup(tgEventos);
        btnAddInanimado.setToggleGroup(tgEventos);
        btnRemoveElemento.setToggleGroup(tgEventos);

        btnUndo= new Button("Undo");
        btnRedo=new Button("Redo");

        getItems().addAll(btnUndo,btnRedo,new Separator(),btnEventoSol,btnEventoHerbicida,btnEventoForca, new Separator(), btnAddFauna,btnAddFlora,btnAddInanimado,btnRemoveElemento);
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
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.HERBICIDA);
            }
            else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

        btnEventoForca.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.FORCA);
            }
            else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

        btnAddFauna.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.ADD_FAUNA);
            }else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

        btnAddFlora.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.ADD_FLORA);
            }else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

        btnAddInanimado.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.ADD_INANIMADO);
            }else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

        btnRemoveElemento.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.REMOVE_ELEMENTO);
            }else
                pcs.firePropertyChange(PROP_TOGGLES,null, ToolbarToggles.NONE);
        });

    }

    private void update() {
        btnUndo.setDisable(!manager.hasUndo());
        btnRedo.setDisable(!manager.hasRedo());
    }
}
