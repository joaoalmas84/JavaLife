package pt.isec.pa.javalife.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;

public class MenuIntervalo extends BorderPane {
    SimulacaoManager simulacaoManager;
    private long intervalo;
    private Label lblIntervalo;
    private TextField txtIntervalo;
    private Button btnSetIntervalo;
    public MenuIntervalo(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
        
    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        lblIntervalo = new Label("Intervalo:");
        txtIntervalo = new TextField();
        btnSetIntervalo = new Button("Set");
        txtIntervalo.setPromptText("Intervalo em milisegundos");
        setLeft(lblIntervalo);
        setCenter(txtIntervalo);
        HBox hBox = new HBox(lblIntervalo, txtIntervalo, btnSetIntervalo);
        hBox.setSpacing(5);
        setCenter(hBox);
    }

    private void update() {
        if(!(simulacaoManager.getState() == SimulacaoState.INTERVALO)) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

    }
}
