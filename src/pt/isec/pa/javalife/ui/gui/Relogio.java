package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.javalife.model.data.Ecossistema;
import pt.isec.pa.javalife.model.data.SimulacaoManager;


public class Relogio extends BorderPane {
    SimulacaoManager simulacaoManager;
    Label tempo;

    public Relogio(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerEcossistema(Ecossistema.PROP_UPDATE_MAP ,(evt) -> Platform.runLater(this::update));
    }
    private void createViews() {
        tempo = new Label("Tempo: 0");
        tempo.setStyle("-fx-font-size: 20px;");
        this.setCenter(tempo);
    }
    private void update() {
        tempo.setText("Tempo: " + simulacaoManager.getTempo());
    }
}
