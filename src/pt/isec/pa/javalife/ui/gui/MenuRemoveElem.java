package pt.isec.pa.javalife.ui.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.data.SimulacaoState;

public class MenuRemoveElem extends BorderPane {
    SimulacaoManager simulacaoManager;
    Button btnGoBack, btnRemoveElemento;
    ChoiceBox<String> cb;
    TextField tfID;
    Label lblTitle, label_ID;
    int id;
    String tipo;

    public MenuRemoveElem(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        id = -1;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());
        cb.setOnAction(e -> {
            tipo = cb.getValue();
            System.out.println(tipo);
        });

        btnGoBack.setOnAction(e -> {
            simulacaoManager.resume();
            simulacaoManager.setState(SimulacaoState.NULL);
        });

        btnRemoveElemento.setOnAction(e -> {
            if (tipo == null || tipo.isEmpty() || id < -1) {
                System.out.println("vou remover merda");
                return;
            }

            System.out.println("vou remover");
            switch (tipo) {
                case "Flora" -> simulacaoManager.removerElemento(id, "Flora");
                case "Fauna" -> simulacaoManager.removerElemento(id, "Fauna");
                case "Inanimado" -> simulacaoManager.removerElemento(id, "Inanimado");
            }

            simulacaoManager.resume();
            simulacaoManager.setState(SimulacaoState.NULL);

        });

        tfID.setOnKeyReleased(e -> {
            try {
                String text = tfID.getText();
                id = text.isEmpty() ? -1 : Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                id = -1;
            }
            System.out.println(id + " : id");
        });
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        lblTitle = new Label("Remover Elemento");

        cb = new ChoiceBox<>(FXCollections.observableArrayList("Flora", "Fauna", "Inanimado"));

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
        System.out.println("remover add elementos");
        System.out.println(simulacaoManager.getState());
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
