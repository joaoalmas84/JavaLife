package pt.isec.pa.javalife.ui.gui;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class ToolbarSimulacao extends ToolBar {

    private static final int BTN_SIZE = 40;
    private static final int BTN_IMG_SIZE = BTN_SIZE -10;

    Button btnEventoSol;
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

        btnEventoSol = new Button(null,rectSol);
        btnEventoSol.setPrefSize(BTN_SIZE,BTN_SIZE);

        getItems().add(btnEventoSol);
    }

    private void registerHandlers() {
    }

    private void update() {
    }
}
