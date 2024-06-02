package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.*;

public class menuEditeElem  extends BorderPane {
    SimulacaoManager simulacaoManager;
    Button btnGoBack, btnEdit;
    TextField tfXI, tfYI, tfForca, tfvelocidade, tfNumReproducoes, tfElemLargura, tfElemAltura;
    HBox hbForca, hbaria, hbVelocidade, hbNumReproducoes;


    public menuEditeElem(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        setProp();
        btnGoBack.setOnAction(e -> simulacaoManager.setState(SimulacaoState.NULL));

        btnEdit.setOnAction(e -> {
            if(simulacaoManager.getState() == SimulacaoState.EDITFAUNA){
                double xI, yI, forca, velocidade, largura, altura;
                try{
                    String text = tfXI.getText();
                    xI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfYI.getText();
                    yI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfForca.getText();
                    forca = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfvelocidade.getText();
                    velocidade = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfElemLargura.getText();
                    largura = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfElemAltura.getText();
                    altura = text.isEmpty() ? -1.0 : Double.parseDouble(text);
                } catch (NumberFormatException ex) {
                    return ;
                }
                if(xI != -1.0 || yI != -1.0 || forca != -1.0 || velocidade != -1.0){
                    simulacaoManager.editFauna(GuardarUltimo.getId(), xI, yI, forca, velocidade);
                }
                setTamalho(largura, altura, Elemento.FLORA, GuardarUltimo.getId());

            }else if(simulacaoManager.getState() == SimulacaoState.EDITFLORA){
                double xI, yI, forca, largura, altura;
                int numReproducoes;
                try{
                    String text = tfXI.getText();
                    xI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfYI.getText();
                    yI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfForca.getText();
                    forca = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfNumReproducoes.getText();
                    numReproducoes = text.isEmpty() ? -1 : Integer.parseInt(text);

                    text = tfElemLargura.getText();
                    largura = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfElemAltura.getText();
                    altura = text.isEmpty() ? -1.0 : Double.parseDouble(text);
                } catch (NumberFormatException ex) {
                    return ;
                }
                if(xI != -1.0 || yI != -1.0 || forca != -1.0 || numReproducoes != -1){
                   simulacaoManager.editFlora(GuardarUltimo.getId(), xI, yI, forca, numReproducoes);
                }
                setTamalho(largura, altura, Elemento.FLORA, GuardarUltimo.getId());
            }else if(simulacaoManager.getState() == SimulacaoState.EDITINANIMADO){
                double xI, yI, largura, altura;
                try{
                    String text = tfXI.getText();
                    xI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfYI.getText();
                    yI = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfElemLargura.getText();
                    largura = text.isEmpty() ? -1.0 : Double.parseDouble(text);

                    text = tfElemAltura.getText();
                    altura = text.isEmpty() ? -1.0 : Double.parseDouble(text);
                } catch (NumberFormatException ex) {
                    return ;
                }
                if(xI != -1.0 || yI != -1.0){
                    simulacaoManager.editInanimado(GuardarUltimo.getId(), xI, yI);
                }
                setTamalho(largura, altura, Elemento.INANIMADO, GuardarUltimo.getId());
            }
            simulacaoManager.setState(SimulacaoState.NULL);
        });
    }

    private void setTamalho(double largura, double altura, Elemento elemento, int id) {
        if(largura != -1.0)
            simulacaoManager.mudarAlturaElem(id, elemento, altura);
        if(altura != -1.0)
            simulacaoManager.mudarLarguraElem(id, elemento, largura);
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> Platform.runLater(this::update));
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));
        btnGoBack = new Button("Go Back");
        btnEdit = new Button("Edit");
        tfXI = new TextField();
        tfYI = new TextField();
        tfForca = new TextField();
        tfNumReproducoes = new TextField();
        tfvelocidade = new TextField();
        tfElemLargura = new TextField();
        tfElemAltura = new TextField();

        hbForca = new HBox(new Label("Forca: "), tfForca);
        hbForca.setAlignment(Pos.CENTER);
        hbForca.setSpacing(10);
        hbaria = new HBox(new Label("X: "), tfXI, new Label("Y: "), tfYI, new Label("Largura: "), tfElemLargura, new Label("Altura: "), tfElemAltura);
        hbaria.setAlignment(Pos.CENTER);
        hbaria.setSpacing(10);
        hbVelocidade = new HBox(new Label("Velocidade: "), tfvelocidade);
        hbVelocidade.setAlignment(Pos.CENTER);
        hbVelocidade.setSpacing(10);
        HBox hbB = new HBox(btnEdit, btnGoBack);
        hbB.setAlignment(Pos.CENTER);
        hbB.setSpacing(10);
        hbNumReproducoes = new HBox(new Label("Numero de Reproducoes: "), tfNumReproducoes);
        hbNumReproducoes.setAlignment(Pos.CENTER);
        hbNumReproducoes.setSpacing(10);
        VBox vb = new VBox(hbNumReproducoes, hbForca, hbaria, hbVelocidade, hbB);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10);
        this.setCenter(vb);



    }

    private void update() {

        if(!(simulacaoManager.getState() == SimulacaoState.EDITFAUNA
                || simulacaoManager.getState() == SimulacaoState.EDITFLORA
                || simulacaoManager.getState() == SimulacaoState.EDITINANIMADO))
        {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        if(simulacaoManager.getState() == SimulacaoState.EDITFAUNA){
            for(IElemento e : simulacaoManager.getElementos()){
                if(GuardarUltimo.getId() == e.getId()  && e.getType() == Elemento.FAUNA){
                    Fauna fauna = (Fauna) e;
                    tfXI.setText(String.valueOf(fauna.getArea().xi()));
                    tfYI.setText(String.valueOf(fauna.getArea().yi()));
                    tfElemLargura.setText(String.valueOf(fauna.getArea().width()));
                    tfElemAltura.setText(String.valueOf(fauna.getArea().height()));
                    tfForca.setText(String.valueOf(fauna.getForca()));
                    tfvelocidade.setText(String.valueOf(fauna.getVelocidade()));
                    break;
                }
            }
            hbVelocidade.setVisible(true);
            hbForca.setVisible(true);
            hbNumReproducoes.setVisible(false);
        }else if(simulacaoManager.getState() == SimulacaoState.EDITFLORA){
            for(IElemento e : simulacaoManager.getElementos()){
                if(GuardarUltimo.getId() == e.getId() && e.getType() == Elemento.FLORA){
                    Flora flora = (Flora) e;
                    tfXI.setText(String.valueOf(flora.getArea().xi()));
                    tfYI.setText(String.valueOf(flora.getArea().yi()));
                    tfElemLargura.setText(String.valueOf(flora.getArea().width()));
                    tfElemAltura.setText(String.valueOf(flora.getArea().height()));
                    tfForca.setText(String.valueOf(flora.getForca()));
                    tfNumReproducoes.setText(String.valueOf(flora.getNumReproducoes()));
                    break;
                }
            }
            hbVelocidade.setVisible(false);
            hbForca.setVisible(true);
            hbNumReproducoes.setVisible(true);
        }else if(simulacaoManager.getState() == SimulacaoState.EDITINANIMADO){
            for(IElemento e : simulacaoManager.getElementos()){
                if(GuardarUltimo.getId() == e.getId()  && e.getType() == Elemento.INANIMADO){
                    Inanimado inanimado = (Inanimado) e;
                    tfXI.setText(String.valueOf(inanimado.getArea().xi()));
                    tfYI.setText(String.valueOf(inanimado.getArea().yi()));
                    tfElemLargura.setText(String.valueOf(inanimado.getArea().width()));
                    tfElemAltura.setText(String.valueOf(inanimado.getArea().height()));
                    break;
                }
            }
            hbVelocidade.setVisible(false);
            hbForca.setVisible(false);
            hbNumReproducoes.setVisible(false);
        }

    }
}
