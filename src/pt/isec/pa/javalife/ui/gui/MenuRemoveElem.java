package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Elemento;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;

import javax.swing.text.html.parser.Element;

public class MenuRemoveElem extends BorderPane {
    SimulacaoManager simulacaoManager;
    Button btnGoBack, btnRemoveElemento;
    ChoiceBox<Elemento> cb;
    TextField tfID;
    Label lblTitle, label_ID;
    int id;
    Elemento tipo;

    public MenuRemoveElem(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        id = -1;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        setProp();
        cb.setOnAction(e -> {
            tipo = cb.getValue();
            System.out.println(tipo);
        });

        btnGoBack.setOnAction(e -> simulacaoManager.setState(SimulacaoState.NULL));

        btnRemoveElemento.setOnAction(e -> {
            if (tipo == null || id < -1) {
                return;
            }

            simulacaoManager.removerElemento(id,tipo);
            simulacaoManager.setState(SimulacaoState.NULL);

        });

        tfID.setOnKeyReleased(e -> {
            try {
                String text = tfID.getText();
                id = text.isEmpty() ? -1 : Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                id = -1;
            }
            //System.out.println(id + " : id");
        });
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        lblTitle = new Label("Remover Elemento");

        cb = new ChoiceBox<>(FXCollections.observableArrayList(Elemento.FLORA,Elemento.FAUNA,Elemento.INANIMADO));

        label_ID = new Label("ID :");
        tfID = new TextField();
        TextFormatter<String> textFormatterID = textFormatter();

        tfID.setTextFormatter(textFormatterID);

        HBox HBox1 = new HBox(label_ID, tfID);
        HBox1.setAlignment(Pos.CENTER);
        HBox1.setSpacing(5);
        btnGoBack = new Button("Go Back");
        btnRemoveElemento = new Button("Remover Elemento");

        HBox HBox2 = new HBox(btnRemoveElemento, btnGoBack);
        HBox2.setAlignment(Pos.CENTER);
        HBox2.setSpacing(25);

        VBox vBox = new VBox(cb, HBox1, HBox2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        setCenter(vBox);

    }

    private void update() {
        //System.out.println("remover add elementos");
        //System.out.println(simulacaoManager.getState());
        if(!(simulacaoManager.getState() == SimulacaoState.REMOVE)){
            this.setVisible(false);
            return ;
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
}
