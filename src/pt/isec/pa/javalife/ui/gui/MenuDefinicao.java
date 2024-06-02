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
    Button btnConfirm, btnGoBack;
    TextField tfAltura, tfLargura,tfDano,tfRegen;

    MenuDefinicao(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {

        setProp();

        btnConfirm.setOnAction(e -> {
            if(!tfAltura.getText().isEmpty()){
                simulacaoManager.setAltura(getTextFieldData(tfAltura));
            }
            if(!tfLargura.getText().isEmpty()){
                simulacaoManager.setLargura(getTextFieldData(tfLargura));
            }
            if(!tfDano.getText().isEmpty()){
                simulacaoManager.setDanoFauna(getTextFieldData(tfDano));
            }
            if(!tfRegen.getText().isEmpty()){
                simulacaoManager.setRegenFlora(getTextFieldData(tfRegen));
            }
        });

        btnGoBack.setOnAction(e -> simulacaoManager.setState(SimulacaoState.NULL));
    }

    private double getTextFieldData(TextField textField){
        try {
            String text = textField.getText();
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            System.out.println("Valor Inválido");
        }
        return -1;
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        btnConfirm = new Button("Confirmar");
        btnGoBack = new Button("Voltar");

        tfAltura = new TextField();
        tfLargura = new TextField();
        tfDano = new TextField();
        tfRegen = new TextField();

        tfAltura.setTextFormatter(textFormatterInteiro());
        tfLargura.setTextFormatter(textFormatterInteiro());
        tfDano.setTextFormatter(textFormatterDecimal());
        tfRegen.setTextFormatter(textFormatterDecimal());

        tfAltura.setPromptText(String.format("%.2f", simulacaoManager.getAlturaEcossistema()));
        tfLargura.setPromptText(String.format("%.2f", simulacaoManager.getLarguraEcossistema()));
        tfDano.setPromptText(String.format("%.2f", simulacaoManager.getDanoFauna()));
        tfRegen.setPromptText(String.format("%.2f", simulacaoManager.getRegenFlora()));
        HBox HBox1 = new HBox(new Label("Altura : "), tfAltura);
        HBox HBox2 = new HBox(new Label("Largura : "), tfLargura);
        HBox HBox3 = new HBox(new Label("Dano da fauna : "), tfDano);
        HBox HBox4 = new HBox(new Label("Regeneração da flora : "), tfRegen);

        HBox1.setAlignment(Pos.CENTER);
        HBox2.setAlignment(Pos.CENTER);
        HBox3.setAlignment(Pos.CENTER);
        HBox4.setAlignment(Pos.CENTER);
        HBox1.setSpacing(10);
        HBox2.setSpacing(10);
        HBox3.setSpacing(10);
        HBox4.setSpacing(10);

        HBox hBoxBotoes=new HBox(btnConfirm,btnGoBack);
        hBoxBotoes.setAlignment(Pos.CENTER);
        hBoxBotoes.setSpacing(10);

        VBox vBox = new VBox(HBox1, HBox2,HBox3,HBox4,hBoxBotoes );
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void update() {

        tfAltura.setDisable(simulacaoManager.getCurrentState_Of_GameEngine()==GameEngineState.PAUSED);
        tfLargura.setDisable(simulacaoManager.getCurrentState_Of_GameEngine()==GameEngineState.PAUSED);

        if(!(simulacaoManager.getState() == SimulacaoState.DEFINICOES)) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private TextFormatter<String> textFormatterInteiro() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
    }

    private TextFormatter<String> textFormatterDecimal() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        });
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }
}
