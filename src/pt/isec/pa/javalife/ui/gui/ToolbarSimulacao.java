package pt.isec.pa.javalife.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class ToolbarSimulacao extends ToolBar {

    private static final int BTN_SIZE = 40;
    private static final int BTN_IMG_SIZE = BTN_SIZE -10;

    Button btnEventoSol;

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

        getItems().addAll(btnEventoSol,btnEventoHerbicida,btnEventoForca);
    }

    private void registerHandlers() {
        btnEventoHerbicida.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                manager.setHerbicida(true);
            }
            else
                manager.setHerbicida(false);
        });

    }

    private void update() {
    }
}
