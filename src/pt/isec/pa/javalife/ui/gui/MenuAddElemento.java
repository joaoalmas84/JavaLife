package pt.isec.pa.javalife.ui.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.*;

public class MenuAddElemento extends BorderPane {
    SimulacaoManager simulacaoManager;
    Button btnGoBack, btnAddElemento;
    ChoiceBox<Elemento> cb;
    TextField tfXI, tfYI, tfXF, tfYF;
    Label lblTitle, label_xI, label_yI, label_xF, label_yF;
    Elemento tipo;
    Double xI, yI, xF, yF;

    public MenuAddElemento(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        xI = yI = xF = yF = -1.0;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        if(!(simulacaoManager.getState() == SimulacaoState.ADD)){
            this.setVisible(false);
            return ;
        }
        this.setVisible(true);
    }

    private void registerHandlers() {

        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());

        cb.setOnAction(e -> tipo = cb.getValue());

        btnGoBack.setOnAction(e -> simulacaoManager.setState(SimulacaoState.NULL));

        btnAddElemento.setOnAction(e -> {
            if (tipo == null || new Area(xI, yI, xF, yF).isInvalid()) {
                return;
            }

            tipo.makeElemento(xI,yI,xF,yF,simulacaoManager);
            simulacaoManager.setState(SimulacaoState.NULL);
        });

        tfXI.setOnKeyReleased(e -> {
            try {
                String text = tfXI.getText();
                xI = text.isEmpty() ? -1.0 : Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                xI = -1.0;
            }
            System.out.println(xI + " XI");
        });

        tfYI.setOnKeyReleased(e -> {
            try {
                String text = tfYI.getText();
                yI = text.isEmpty() ? -1.0 : Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                yI = -1.0;
            }
            System.out.println(yI + " YI");
        });

        tfXF.setOnKeyReleased(e -> {
            try {
                String text = tfXF.getText();
                xF = text.isEmpty() ? -1.0 : Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                xF = -1.0;
            }
            System.out.println(xF + " XF");
        });

        tfYF.setOnKeyReleased(e -> {
            try {
                String text = tfYF.getText();
                yF = text.isEmpty() ? -1.0 : Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                yF = -1.0;
            }
            System.out.println(yF + " YF");
        });

    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        lblTitle = new Label("add elementos");

        cb = new ChoiceBox<>(FXCollections.observableArrayList(Elemento.FLORA,Elemento.FAUNA,Elemento.INANIMADO));

        label_xI = new Label("xI :");
        label_yI = new Label("yI :");
        label_xF = new Label("xF :");
        label_yF = new Label("yF :");

        tfXI = new TextField();
        tfYI = new TextField();
        tfXF = new TextField();
        tfYF = new TextField();

        // Create a new TextFormatter for each TextField
        TextFormatter<String> textFormatterXI = textFormatter();

        TextFormatter<String> textFormatterYI = textFormatter();

        TextFormatter<String> textFormatterXF = textFormatter();

        TextFormatter<String> textFormatterYF = textFormatter();

        tfXI.setTextFormatter(textFormatterXI);
        tfYI.setTextFormatter(textFormatterYI);
        tfXF.setTextFormatter(textFormatterXF);
        tfYF.setTextFormatter(textFormatterYF);

        HBox HBox1 = new HBox(label_xI, tfXI, label_yI, tfYI, label_xF, tfXF, label_yF, tfYF);
        HBox1.setAlignment(Pos.CENTER);
        HBox1.setSpacing(5);
        btnGoBack = new Button("Go Back");
        btnAddElemento = new Button("Add Elemento");
        HBox HBox2 = new HBox(btnAddElemento, btnGoBack);
        HBox2.setAlignment(Pos.CENTER);
        HBox2.setSpacing(25);

        VBox vBox = new VBox(cb, HBox1, HBox2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        setCenter(vBox);
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
