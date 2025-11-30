package com.ecocity.model.state;

import com.ecocity.GameManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Random;

public class MenuState implements GameState {
    
    private StackPane root;

    public MenuState(GameManager context) {
        root = new StackPane();
        
        // 1. FOND : Dégradé Ciel (Nuit/Aube)
        Rectangle bg = new Rectangle(800, 600);
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#1a2a6c")), new Stop(1, Color.web("#b21f1f")) };
        LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        bg.setFill(lg);

        // 2. DÉCOR : Skyline (Immeubles en ombre chinoise en bas)
        Group skyline = createSkyline();

        // 3. UI : Titre et Boutons
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        Text title = new Text("ECO CITY\nDEFENDER");
        title.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 50px; -fx-fill: white; -fx-stroke: black; -fx-stroke-width: 2px; -fx-text-alignment: center;");
        
        Text subTitle = new Text("Sauvez la ville de la pollution !");
        subTitle.setStyle("-fx-font-size: 18px; -fx-fill: lightgray;");

        Button btnStart = createStyledButton("Démarrer la Mission");
        btnStart.setOnAction(e -> context.setState(new PlayingState(context)));

        Button btnQuit = createStyledButton("Quitter");
        btnQuit.setOnAction(e -> System.exit(0));

        menuBox.getChildren().addAll(title, subTitle, btnStart, btnQuit);

        // Assemblage des couches
        root.getChildren().addAll(bg, skyline, menuBox);
    }

    // Génère des rectangles noirs aléatoires pour simuler une ville au loin
    private Group createSkyline() {
        Group g = new Group();
        Random rand = new Random();
        int x = 0;
        while (x < 800) {
            int width = 30 + rand.nextInt(50);
            int height = 50 + rand.nextInt(150);
            Rectangle building = new Rectangle(x, 600 - height, width, height);
            building.setFill(Color.rgb(20, 20, 40)); // Bleu très sombre
            g.getChildren().add(building);
            
            // Quelques fenêtres allumées
            for(int i=0; i<5; i++) {
                if(rand.nextBoolean()) {
                    Rectangle win = new Rectangle(x + 5 + rand.nextInt(width-10), 600 - height + 10 + rand.nextInt(height-20), 4, 4);
                    win.setFill(Color.YELLOW);
                    g.getChildren().add(win);
                }
            }
            x += width;
        }
        return g;
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 5;"
        );
        // Effet de survol simple
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;"));
        return btn;
    }

    @Override public void handleInput(GameManager context) {}
    @Override public void update() {}
    @Override public void render() {
        GameManager.getInstance().getRoot().setCenter(root);
    }
    
    // Import nécessaire pour le Group interne
    class Group extends javafx.scene.Group {} 
}