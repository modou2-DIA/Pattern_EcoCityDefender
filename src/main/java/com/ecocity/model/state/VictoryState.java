package com.ecocity.model.state;



import com.ecocity.GameManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VictoryState implements GameState {
    @Override
    public void handleInput(GameManager context) {}
    @Override
    public void update() {}

    @Override
    public void render() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: lightblue;"); // Ciel bleu = Victoire

        Text text = new Text("VICTOIRE !");
        text.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-fill: green;");
        
        Text sub = new Text("La ville est sauvée.");
        sub.setStyle("-fx-font-size: 20px;");

        Button btnRestart = new Button("Rejouer");
        btnRestart.setOnAction(e -> GameManager.getInstance().setState(new MenuState(GameManager.getInstance())));

        layout.getChildren().addAll(text, sub, btnRestart);
        GameManager.getInstance().getRoot().setCenter(layout);
    }
}
