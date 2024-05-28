package pt.isec.pa.javalife.ui.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.ui.gui.res.ImageManager;

import java.util.HashSet;
import java.util.Set;

public class SimulacaoArea extends Canvas {
    private final SimulacaoManager simulacaoManager;
    private double x, y;
    double altura, largura;
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
        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, (evt) -> Platform.runLater(this::update));
        simulacaoManager.addPropertyChangeListenerEcossistema(Ecossistema.PROP_UPDATE_MAP , (evt) -> Platform.runLater(this::update));
        simulacaoManager.addPropertyChangeListener(SimulacaoManager.PROP_UPDATE_COMMAND, (evt) -> Platform.runLater(this::update));
        //this.setOnMousePressed(evt -> update());
        this.setOnMouseMoved(evt -> {
            x = evt.getX();
            y = evt.getY();
            update();
        });

        this.setOnMouseClicked(evt -> {
            x = evt.getX();
            y = evt.getY();
            for (IElemento elem : simulacaoManager.getElementos()) {
                if(elem.getArea().isPointOverlapping(x,y)){
                    System.out.println(elem);
                    return ;
                }
            }
        });
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
        simulacaoManager.getElementos().forEach( elemento -> drawElemento(gc,elemento));

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
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN.darker());
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
        Image imageView = ImageManager.getImage(Imagns);
        if(imageView == null) return;
        gc.setFill(new ImagePattern(imageView));
        gc.setStroke(color.darker());
        gc.fillRect(x, y, width, height);
    }

}
