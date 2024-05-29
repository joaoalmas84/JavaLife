package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;

public class MenuIntervalo extends BorderPane {
    SimulacaoManager simulacaoManager;
    private Label lblIntervalo;
    private TextField txtIntervalo;
    private Button btnSetIntervalo;
    private Button btnCancel;

    private Label label;

    public MenuIntervalo(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
        
    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_ADD_LIS, evt -> Platform.runLater(this::setProp));
        setProp();

        btnSetIntervalo.setOnAction(event ->{
            int intervalo;
            try {
                String text = txtIntervalo.getText();
                if(text.isEmpty()){
                    return;
                }
                intervalo = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                intervalo = 250;
            }
            System.out.println(intervalo + " intervalo");
            if (!simulacaoManager.mudarTempo(intervalo)) {
                label.setText("Intervalo inválido. Tem que ser maior ou igual a 10");
                label.setStyle("-fx-text-fill: red");
            } else {
                simulacaoManager.setState(SimulacaoState.NULL);
            }
        });

    txtIntervalo.setOnKeyPressed(keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnSetIntervalo.fire();
        }
    });

        btnCancel.setOnAction(event ->{
            simulacaoManager.setState(SimulacaoState.NULL);
        });

    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        lblIntervalo = new Label("Intervalo:");
        txtIntervalo = new TextField();
        btnSetIntervalo = new Button("Set");
        btnCancel = new Button("Cancel");
        txtIntervalo.setPromptText("Intervalo em milisegundos");
        HBox hbButtons=new HBox(btnSetIntervalo,btnCancel);
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(15);

        HBox hbInput = new HBox(lblIntervalo, txtIntervalo);
        hbInput.setAlignment(Pos.CENTER);
        hbInput.setSpacing(15);

        label = new Label();

        VBox vBox = new VBox(hbInput,hbButtons, label);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        setCenter(vBox);


    }

    private void update() {
        if(!(simulacaoManager.getState() == SimulacaoState.INTERVALO)) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

    }
}
