package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MainJFX extends Application {
    private SimulacaoManager simulacaoManager;

    @Override
    public void init() throws Exception {
        super.init();
        simulacaoManager = new SimulacaoManager();
    }

    @Override
    public void start(Stage stage) {
        createOneStage(stage);
        createOneStage(new Stage());
        createOneStageRelogio(new Stage());
    }

    private void createOneStage(Stage stage) {
        RootPane root = new RootPane(simulacaoManager);
        Scene scene = new Scene(root,800,450);
        stage.setScene(scene);
        stage.setTitle("Simulação");
        stage.show();
    }

    private void createOneStageRelogio(Stage stage) {
        Relogio root = new Relogio(simulacaoManager);
        Scene scene = new Scene(root,200,100);
        stage.setScene(scene);
        stage.setTitle("Tempo");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        simulacaoManager.stop();
    }
}
