package pt.isec.pa.javalife.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.ui.gui.res.CSSManager;

public class RootPane extends BorderPane {
    SimulacaoManager simulacaoManager;
    SimulacaoArea simulacaoArea;
    Pane areaPane;

    VBox vb;


    public RootPane(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;

        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");


        simulacaoArea = new SimulacaoArea(simulacaoManager);
        areaPane = new Pane(simulacaoArea);

        vb=new VBox(new ToolbarSimulacao(simulacaoManager),areaPane);
        VBox.setVgrow(areaPane, Priority.ALWAYS);

        setTop( new MenuSimulacao(simulacaoManager));
        StackPane stackPane = new StackPane(
                vb,
                new MainMenuUI(simulacaoManager),
                new MenuAddElemento(simulacaoManager),
                new MenuRemoveElem(simulacaoManager),
                new MenuIntervalo(simulacaoManager),
                new MenuDefinicao(simulacaoManager),
                new menuEditeElem(simulacaoManager)
        );
        setCenter(stackPane);
    }

    private void registerHandlers() {
        areaPane.widthProperty().addListener(observable -> simulacaoArea.updateSize(areaPane.getWidth(),areaPane.getHeight()));
        areaPane.heightProperty().addListener(observable -> simulacaoArea.updateSize(areaPane.getWidth(),areaPane.getHeight()));
    }

    private void update() {
    }
}
