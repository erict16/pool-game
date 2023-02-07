package PoolGame;

import java.util.List;
import PoolGame.state.Easy;
import PoolGame.state.Hard;
import PoolGame.state.Level;
import PoolGame.state.Normal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/** Main application entry point. */
public class App extends Application {
    /**
     * @param args First argument is the path to the config file
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /**
     * Starts the application
     * 
     * @param primaryStage The primary stage for the application.
     */
    public void start(Stage primaryStage) {
        GameManager gameManager = new GameManager();
        Button easyLevel =  new Button("Easy");
        Button normalLevel = new Button("Normal");
        Button hardLevel = new Button("Hard");
        Button exit = new Button("Exit");
        TilePane tilePane = new TilePane();

        EventHandler<ActionEvent> eventEasy = e -> {
            // Load Easy level
            Level easy = new Easy(gameManager);
            easy.loadGame();

            gameManager.buildManager();
            gameManager.run();
            primaryStage.setTitle("Pool");
            primaryStage.setScene(gameManager.getScene());
            primaryStage.show();
        };


        EventHandler<ActionEvent> eventNormal = e -> {
            // Load Normal level
            Level normal = new Normal(gameManager);
            normal.loadGame();

            gameManager.buildManager();
            gameManager.run();
            primaryStage.setTitle("Pool");
            primaryStage.setScene(gameManager.getScene());
            primaryStage.show();
        };

        EventHandler<ActionEvent> eventHard = e -> {
            // Load hard level
            Level hard = new Hard(gameManager);
            hard.loadGame();

            gameManager.buildManager();
            gameManager.run();
            primaryStage.setTitle("Pool");
            primaryStage.setScene(gameManager.getScene());
            primaryStage.show();
        };

        EventHandler<ActionEvent> eventExit = e -> {
            System.exit(0);
        };

        easyLevel.setOnAction(eventEasy);
        normalLevel.setOnAction(eventNormal);
        hardLevel.setOnAction(eventHard);
        exit.setOnAction(eventExit);

        easyLevel.setTranslateX(60);
        easyLevel.setTranslateY(100);
        normalLevel.setTranslateX(70);
        normalLevel.setTranslateY(100);
        hardLevel.setTranslateX(80);
        hardLevel.setTranslateY(100);
        exit.setTranslateX(80);

        tilePane.getChildren().add(easyLevel);
        tilePane.getChildren().add(normalLevel);
        tilePane.getChildren().add(hardLevel);
        tilePane.getChildren().add(exit);
        primaryStage.setScene(new Scene(tilePane, 300, 200));
        primaryStage.show();

    }

    /**
     * Checks if the config file path is given as an argument.
     *
     * @param args
     * @return config path.
     */
    private static String checkConfig(List<String> args) {
        String configPath;
        if (args.size() > 0) {
            configPath = args.get(0);
        } else {
            configPath = "src/main/resources/config_normal.json";
        }
        return configPath;
    }
}
