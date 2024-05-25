package pt.isec.pa.javalife.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.ui.gui.res.CSSManager;

public class RootPane extends BorderPane {
    SimulacaoManager simulacaoManager;
    SimulacaoArea simulacaoArea;
    Pane areaPane;

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
        setTop( new MenuSimulacao(simulacaoManager));
        StackPane stackPane = new StackPane(
                areaPane,
                new MainMenuUI(simulacaoManager),
                new MenuAddElemento(simulacaoManager),
                new MenuRemoveElem(simulacaoManager),
                new MenuIntervalo(simulacaoManager)
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
