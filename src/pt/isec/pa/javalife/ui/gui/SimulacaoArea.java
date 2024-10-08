package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.ui.gui.res.MultitonImage;

import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.Set;

public class SimulacaoArea extends Canvas {
    private final SimulacaoManager simulacaoManager;
    private double x, y;
    double altura, largura;

    ToolbarSimulacao toolbarSimulacao;
    ToolbarToggles toggled;

    public SimulacaoArea(SimulacaoManager simulacaoManager,ToolbarSimulacao tbs) {
        super(500,500);
        this.simulacaoManager = simulacaoManager;
        toolbarSimulacao=tbs;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() {
        setProp();
        //this.setOnMousePressed(evt -> update());
        this.setOnMouseMoved(evt -> {
            x = evt.getX();
            y = evt.getY();
            update();
        });
        this.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                handleMouseClicked(event);
            }else if(event.getButton() == MouseButton.SECONDARY){
                handleMouseClikdEdit(event);
            }

        });
    }

    private void handleMouseClicked(MouseEvent evt){
        double x_ = evt.getX()/largura;
        double y_ = evt.getY()/altura;
        for (IElemento elem : simulacaoManager.getElementos()) {
            if(elem.getArea().isPointOverlapping(x_,y_)){
                System.out.println(elem);
                if(toggled==ToolbarToggles.HERBICIDA && elem.getType()== Elemento.FLORA || toggled==ToolbarToggles.REMOVE_ELEMENTO){
                    simulacaoManager.removerElemento(elem.getId(),elem.getType());
                }
                else if(toggled==ToolbarToggles.FORCA && elem.getType()==Elemento.FAUNA){
                    simulacaoManager.evAddForca((Fauna)elem);
                }else if(elem.getType()!=Elemento.INANIMADO){
                    break;
                }
                update();
                return;
            }
        }
        if(toggled==ToolbarToggles.ADD_FAUNA){
            simulacaoManager.adicionarFauna(x_,y_,x_+30,y_+30);
        }else if(toggled == ToolbarToggles.ADD_FLORA)
            simulacaoManager.adicionarFlora(x_,y_,x_+30,y_+30);
        else if(toggled== ToolbarToggles.ADD_INANIMADO)
            simulacaoManager.adicionarInanimado(x_,y_,x_+30,y_+30);
        update();
    }

    private void handleMouseClikdEdit(MouseEvent evt){
        double x_ = evt.getX()/largura;
        double y_ = evt.getY()/altura;
        for (IElemento elem : simulacaoManager.getElementos()) {
            if(elem.getArea().isPointOverlapping(x_,y_)){
                simulacaoManager.pause();
                GuardarUltimo.setId(elem.getId());
                GuardarUltimo.setTipo(elem.getType());

                if(elem.getType() == Elemento.FAUNA)
                    simulacaoManager.setState(SimulacaoState.EDITFAUNA);
                else if(elem.getType() == Elemento.FLORA)
                    simulacaoManager.setState(SimulacaoState.EDITFLORA);
                else if(elem.getType() == Elemento.INANIMADO)
                    simulacaoManager.setState(SimulacaoState.EDITINANIMADO);

                return ;
            }
        }
    }

    private void setProp() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, (evt) -> Platform.runLater(this::update));
        simulacaoManager.addPropertyChangeListenerEcossistema(Ecossistema.PROP_UPDATE_MAP , (evt) -> Platform.runLater(this::update));
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_UPDATE_COMMAND, (evt) -> Platform.runLater(this::update));
        toolbarSimulacao.addPropertyChangeListener(ToolbarSimulacao.PROP_TOGGLES,(evt) -> Platform.runLater(()-> updateToggles(evt)));
    }

    private void updateToggles(PropertyChangeEvent evt){
        toggled= (ToolbarToggles) evt.getNewValue();
    }

    private void update() {
        if(simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.READY){
            setVisible(false);
            return;
        }
        setVisible(true);

        altura = getHeight() / simulacaoManager.getAlturaEcossistema();
        largura = getWidth() / simulacaoManager.getLarguraEcossistema();
        GraphicsContext gc = this.getGraphicsContext2D();

        clearScreen(gc);
        drawline(gc);
        gc.setLineWidth(2);

        HashSet<IElemento> floraSet = new HashSet<>();
        HashSet<IElemento> outrosSet = new HashSet<>();
        simulacaoManager.getElementos().forEach( elemento -> {
            if(elemento.getType()==Elemento.FLORA){
                floraSet.add(elemento);
            }
            else
                outrosSet.add(elemento);
        });
        floraSet.forEach(elemento -> drawElemento(gc,elemento));
        outrosSet.forEach(elemento -> drawElemento(gc,elemento));


        Set<IElemento> elementos = simulacaoManager.getElementos();

        for (IElemento elem : elementos) {
            if(elem.getArea().isPointOverlapping(x / largura,y / altura)){
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.YELLOW.darker());
                gc.setLineWidth(1);
                gc.fillText(String.valueOf(elem.getId()), x, y);
                Area area = elem.getArea();
                gc.strokeRect(area.xi() * largura, area.yi() * altura,
                        (area.xf() - area.xi()) * largura, (area.yf() - area.yi())* altura);
                break;
            }
        }
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.LIGHTYELLOW);
        gc.fillRect(0,0,getWidth(),getHeight());
    }

    private void drawElemento(GraphicsContext gc, IElemento elemento) {
        if (elemento == null || elemento.getArea() == null || simulacaoManager == null) return;

        altura = getHeight() / simulacaoManager.getAlturaEcossistema();
        largura = getWidth() / simulacaoManager.getLarguraEcossistema();

        Area area = elemento.getArea();
        double x = area.xi();
        double y = area.yi();
        double width = area.xf() - x;
        double height = area.yf() - y;
        switch (elemento.getType()) {
            case Elemento.FLORA -> {
                Color transparentColor = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), ((Flora) elemento).getForca() / 100);
                drawRectangle(gc, x * largura, y * altura, width * largura, height * altura, transparentColor);
            }
            case Elemento.INANIMADO -> drawRectangle(gc, x * largura, y * altura, width * largura, height * altura, Color.GRAY);
            case Elemento.FAUNA -> {
                Fauna fauna = (Fauna) elemento;
                drawRectangleFAUNA(gc, x * largura, y * altura, width * largura, height * altura, Color.RED, fauna.getImagem());
                drawRectanglelive(gc,x * largura , (y - 7) * altura, width * largura, 7 * altura, fauna.getForca());
            }
        }
    }

    private void drawRectanglelive(GraphicsContext gc, double x, double y, double width, double height, double forca) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED.darker());
        gc.setLineWidth(1);
        gc.fillRect(x, y, width * (forca / 100), height);
        gc.strokeRect(x, y, width , height);
    }

    private void drawRectangle(GraphicsContext gc, double x, double y, double width, double height, Color color) {
        gc.setFill(color);
        gc.setStroke(color.darker());
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }

    public void updateSize(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
        update();
    }

    private void drawline(GraphicsContext gc) {
        gc.setLineWidth(0.5);
        gc.setStroke(Color.BLACK);
        int valor = 30;
        for(int i = 0; i < valor; i++){
            gc.strokeLine(0, i * getHeight() / valor, getWidth(), i * getHeight() / valor);
            gc.strokeLine(i * getWidth() / valor, 0, i  * getWidth() / valor, getHeight());
        }
    }

    private void drawRectangleFAUNA(GraphicsContext gc, double x, double y, double width, double height, Color color, String Imagns) {
        Image imageView = MultitonImage.getModel(Imagns);
        if(imageView == null) return;
        gc.setFill(new ImagePattern(imageView));
        gc.setStroke(color.darker());
        gc.fillRect(x, y, width, height);
    }

}
