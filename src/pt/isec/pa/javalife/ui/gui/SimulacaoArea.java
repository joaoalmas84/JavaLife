package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

import java.awt.*;
import java.util.Set;

public class SimulacaoArea extends Canvas {
    private final SimulacaoManager simulacaoManager;
    private double x, y;

    public SimulacaoArea(SimulacaoManager simulacaoManager) {
        super(500,500);
        this.simulacaoManager = simulacaoManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() {
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, (evt) ->{
            Platform.runLater(this::update);
        });
        simulacaoManager.addPropertyChangeListenerEcossistema(Ecossistema.PROP_UPDATE_MAP , (evt) ->{
            Platform.runLater(this::update);
        });
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_UPDATE_COMMAND, (evt) ->{
            Platform.runLater(this::update);
        });
        //this.setOnMousePressed(evt -> update());
        this.setOnMouseMoved(evt -> {
            x = evt.getX();
            y = evt.getY();
            update();
        });
    }

    /*public void updateinfo(int x, int y){
        Set<IElemento> elementos = simulacaoManager.getElementos();
        for (IElemento elem : elementos) {
            if(elem.getArea().isPointOverlapping(x,y)){
                System.out.println("updateinfo simm");
                lblInfo.setText(String.valueOf(elem.getId()));

                lblInfo.setLocation(x, y);
                lblInfo.setVisible(true);
                return ;
            }
        }
        System.out.println("updateinfo nao");
        lblInfo.setVisible(false);
    }*/

    private void update() {
        if(simulacaoManager.getCurrentState_Of_GameEngine() == GameEngineState.READY){
            setVisible(false);
            return;
        }
        setVisible(true);
        GraphicsContext gc = this.getGraphicsContext2D();

        clearScreen(gc);
        drawline(gc);
        gc.setLineWidth(2);
        simulacaoManager.getElementos().forEach( elemento -> drawElemento(gc,elemento));

        Set<IElemento> elementos = simulacaoManager.getElementos();

        for (IElemento elem : elementos) {
            if(elem.getArea().isPointOverlapping(x,y)){
                gc.setFill(Color.YELLOW);
                gc.setLineWidth(5);
                gc.fillText(String.valueOf(elem.getId()), x, y);
                break;
            }
        }

        //drawing.getList().forEach( figure -> drawFigure(gc,figure));
        //drawFigure(gc,drawing.getCurrentFigure());
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.LIGHTYELLOW);
        gc.fillRect(0,0,getWidth(),getHeight());
    }

    private void drawElemento(GraphicsContext gc, IElemento elemento) {
        if (elemento == null || elemento.getArea() == null || simulacaoManager == null) return;

        double altura = getHeight() / simulacaoManager.getAlturaEcossistema();
        double largura = getWidth() / simulacaoManager.getLarguraEcossistema();

        Area area = elemento.getArea();
        double x = area.xi();
        double y = area.yi();
        double width = area.xf() - x;
        double height = area.yf() - y;
        //System.out.println(elemento.getType());
        //System.out.println(x + " " + y + " " + width + " " + height);
        switch (elemento.getType()) {
            case Elemento.FLORA -> {
                Flora flora = (Flora) elemento;
                Color transparentColor = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), ((Flora) elemento).getForca() / 100);
                drawRectangle(gc, x * largura, y * altura, width * largura, height * altura, transparentColor);
            }
            case Elemento.INANIMADO -> drawRectangle(gc, x * largura, y * altura, width * largura, height * altura, Color.GRAY);
            case Elemento.FAUNA -> drawRectangle(gc, x * largura, y * altura, width * largura, height * altura, Color.RED);
        }
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

}
