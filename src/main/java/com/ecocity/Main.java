package com.ecocity;

import com.ecocity.utils.GameLogger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameLogger.getInstance().log("INFO", "Game Started");

        GameManager gameManager = GameManager.getInstance();
        gameManager.initialize();

        Scene scene = new Scene(gameManager.getRoot(), 800, 600);
        primaryStage.setTitle("Eco City Defender - Projet Design Patterns");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}