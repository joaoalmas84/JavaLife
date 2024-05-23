package pt.isec.pa.javalife.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.Simulacao;
import pt.isec.pa.javalife.model.data.SimulacaoManager;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.ui.gui.res.ImageManager;

import java.util.Objects;

public class MainMenuUI extends BorderPane {
    SimulacaoManager simulacaoManager;
    Label lblTitle;
    Button btnStart, btnSettings, btnExit, btnFullScreen;

    public MainMenuUI(SimulacaoManager simulacaoManager) {
        this.simulacaoManager = simulacaoManager;

        //titleFont = FontManager.loadFont("PAC-FONT.TTF",69);
        //buttonsFont = FontManager.loadFont("PressStart2P-Regular.ttf",12);

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,244), null, null)));

        lblTitle = new Label("Simulação");
        //lblTitle.setFont(titleFont);
        lblTitle.setId("mainMenuTitle");

        btnStart = new Button("INICIAR SIMULAÇÃO");
        //btnStart.setFont(buttonsFont);
        btnStart.setId("mainMenuButton");
        btnStart.setStyle("-fx-background-color: #fe6a00");

        btnExit  = new Button("SAIR");
        //btnExit.setFont(buttonsFont);
        btnExit.setId("mainMenuButton");
        btnExit.setStyle("-fx-background-color: #ff0006");

        btnSettings = new Button("DEFINIÇÕES");
        //btnSettings.setFont(buttonsFont);
        btnSettings.setId("mainMenuButton");
        btnSettings.setStyle("-fx-background-color: #00ff06");

        VBox vBox = new VBox(lblTitle, btnStart, btnSettings, btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        VBox.setMargin(btnStart, new Insets(75, 0, 0, 0)); // Set top margin for the button

        btnFullScreen = new Button();
        btnFullScreen.setBackground(
                new Background(
                        new BackgroundImage(
                                Objects.requireNonNull(ImageManager.getImage("fullscreen.png")),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );
        btnFullScreen.setId("fullscreenButton");

        HBox hBox = new HBox(btnFullScreen);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setMargin(btnFullScreen, new Insets(20, 35, 0, 0)); // Set top margin for the button

        this.setCenter(vBox);
        this.setTop(hBox);
    }

    private void registerHandlers() {

        simulacaoManager.addPropertyChangeListenerSimulacao(Simulacao.PROP_UPDATE_SIMULACAO, evt -> update());

        btnFullScreen.setOnAction(event -> {
            Stage stage = (Stage) btnFullScreen.getScene().getWindow();
            if (stage.isFullScreen()) {
                btnFullScreen.setBackground(
                        new Background(
                                new BackgroundImage(
                                        Objects.requireNonNull(ImageManager.getImage("fullscreen.png")),
                                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(1, 1, true, true, true, false)
                                )
                        )
                );

                stage.setFullScreen(false);
            }
            else {
                btnFullScreen.setBackground(
                        new Background(
                                new BackgroundImage(
                                        Objects.requireNonNull(ImageManager.getImage("minimize.png")),
                                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(1, 1, true, true, true, false)
                                )
                        )
                );

                stage.setFullScreen(true);
            }
        });

        /*
        btnStart.setOnAction( event -> {
            if(simulacao.checkSavedGame()){
                Alert alert = new Alert(
                        Alert.AlertType.WARNING,
                        null,
                        ButtonType.YES, ButtonType.NO
                );

                alert.setTitle("Jogo guardado");
                alert.setHeaderText("Foi detetado um jogo previamente guardado.\nPertence continuar [YES] ou começar um novo [NO]?");

                ImageView exitIcon = new ImageView(ImageManager.getImage("pacman-gif.gif"));
                exitIcon.setFitHeight(100);
                exitIcon.setFitWidth(100);
                alert.getDialogPane().setGraphic(exitIcon);

                alert.showAndWait().ifPresent(response -> {
                    switch (response.getButtonData()){
                        case YES -> {
                            tinyPacmanManager.load();
                        }
                        case NO -> {
                            tinyPacmanManager.delete();
                            tinyPacmanManager.start();
                        }
                    }
                });
            }
            else{
                tinyPacmanManager.start();
            }


        });

        btnTop5.setOnAction( event -> {
            tinyPacmanManager.setShowTopFive(true);
        });

        btnCredits.setOnAction( event -> {
            tinyPacmanManager.setShowCredits(true);
        });
 */

        //ExitAlertUI.exitAlert(btnExit);

        btnStart.setOnAction(event -> {
            simulacaoManager.start();
        });

    }

    private void update(){
        if(simulacaoManager.getCurrentState_Of_GameEngine() != GameEngineState.READY) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

}
