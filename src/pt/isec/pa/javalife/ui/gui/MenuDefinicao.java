package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

public class MenuDefinicao extends BorderPane {
    SimulacaoManager simulacaoManager;
    Button btnsetAltura, btnsetLargura, btnGoBack;
    TextField tfAltura, tfLargura;

    MenuDefinicao(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        update();
        createViews();
        registerHandlers();
    }

    private void registerHandlers() {

        setProp();

        btnsetAltura.setOnAction(e -> {
            try {
                String text = tfAltura.getText();
                if (text.isEmpty()) {
                    return;
                }
                double altura = Double.parseDouble(text);
                simulacaoManager.mudarAltura(altura);
            } catch (NumberFormatException ex) {
                System.out.println("Altura inválida");
            }
        });

        btnsetLargura.setOnAction(e -> {
            try {
                String text = tfLargura.getText();
                if (text.isEmpty()) {
                    return;
                }
                double largura = Double.parseDouble(text);
                simulacaoManager.mudarLargura(largura);
            } catch (NumberFormatException ex) {
                System.out.println("Largura inválida");
            }
        });

        btnGoBack.setOnAction(e -> simulacaoManager.setState(SimulacaoState.NULL));


    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        btnsetAltura = new Button("Definir Altura");
        btnsetLargura = new Button("Definir Largura");
        btnGoBack = new Button("Voltar");
        tfAltura = new TextField();
        tfLargura = new TextField();
        tfAltura.setTextFormatter(textFormatter());
        tfLargura.setTextFormatter(textFormatter());
        tfAltura.setPromptText(String.format("%.2f", simulacaoManager.getAlturaEcossistema()));
        tfLargura.setPromptText(String.format("%.2f", simulacaoManager.getLarguraEcossistema()));
        HBox HBox1 = new HBox(new Label("Altura : "), tfAltura, btnsetAltura);
        HBox HBox2 = new HBox(new Label("Largura : "), tfLargura, btnsetLargura);
        HBox1.setAlignment(Pos.CENTER);
        HBox2.setAlignment(Pos.CENTER);
        HBox1.setSpacing(10);
        HBox2.setSpacing(10);
        VBox vBox = new VBox(HBox1, HBox2, btnGoBack);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void update() {
        if(!(simulacaoManager.getState() == SimulacaoState.DEFINICOES && simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.READY)) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private TextFormatter<String> textFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }
}
