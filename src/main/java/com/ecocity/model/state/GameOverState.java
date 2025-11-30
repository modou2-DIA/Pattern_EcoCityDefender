package com.ecocity.model.state;

import com.ecocity.GameManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOverState implements GameState {

    @Override
    public void handleInput(GameManager context) {}

    @Override
    public void update() {}

    @Override
    public void render() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black;");

        Text text = new Text("GAME OVER - La ville est trop polluée !");
        text.setFill(Color.RED);
        text.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Button btnRestart = new Button("Retour au Menu");
        btnRestart.setOnAction(e -> GameManager.getInstance().setState(new MenuState(GameManager.getInstance())));

        layout.getChildren().addAll(text, btnRestart);
        GameManager.getInstance().getRoot().setCenter(layout);
    }
}