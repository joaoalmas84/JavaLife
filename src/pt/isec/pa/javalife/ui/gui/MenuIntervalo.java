package pt.isec.pa.javalife.ui.gui;

import javafx.geometry.Pos;
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
    private Label lblIntervalo;
    private TextField txtIntervalo;
    private Button btnSetIntervalo;
    private Button btnCancel;

    public MenuIntervalo(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
        
    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());

        btnSetIntervalo.setOnAction(event ->{
            int intervalo;
            try {
                String text = txtIntervalo.getText();
                if(text.isEmpty()){
                    return;
                }
                intervalo = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                intervalo = 1000;
            }
            System.out.println(intervalo + " intervalo");
            simulacaoManager.setTempo(intervalo);
            simulacaoManager.setState(SimulacaoState.NULL);
        });

        btnCancel.setOnAction(event ->{
            simulacaoManager.setState(SimulacaoState.NULL);
        });


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

        VBox vBox = new VBox(hbInput,hbButtons);
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
