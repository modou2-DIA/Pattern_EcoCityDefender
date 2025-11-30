package com.ecocity.model.state;

import com.ecocity.GameManager;
import com.ecocity.model.composite.District;
import com.ecocity.model.composite.Building;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox; // Changé VBox pour HBox (plus horizontal)
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.geometry.Pos; // Pour centrer le HUD

import java.util.ArrayList;
import java.util.List;

public class PlayingState implements GameState {
    private Pane gamePane;
    private District cityRoot;
    private AnimationTimer gameLoop;
    
    // UI
    private ProgressBar globalHealthBar;
    private Text timerText;
    private Text pollutionText;
    
    private double timeElapsed = 0;
    private final double TIME_TO_WIN = 30.0; 
    private final double POLLUTION_LIMIT = 50.0; 
    
    private List<Building> activeBuildings = new ArrayList<>();

    public PlayingState(GameManager context) {
        gamePane = new Pane();
        initEnvironment(); 
        createCity();      
        createHUD();       

        cityRoot.render(gamePane); 
        GameManager.getInstance().getRoot().setCenter(gamePane);
        
        startGameLoop();
    }

    private void initEnvironment() {
        // Ciel
        Rectangle sky = new Rectangle(0, 0, 800, 350); // Ligne d'horizon baissée un peu
        sky.setFill(Color.web("#87CEEB")); 
        
        // Sol
        Rectangle ground = new Rectangle(0, 350, 800, 250); // Sol ajusté
        ground.setFill(Color.web("#228B22")); 
        
        gamePane.getChildren().addAll(sky, ground);
    }

    private void createCity() {
        cityRoot = new District("Eco-City");
        District indus = new District("Zone Sud");
        District centre = new District("Centre-Ville");
        District resid = new District("Banlieue Nord");

        // --- DÉCALAGE GÉNÉRAL VERS LE BAS (+80 pixels environ) ---

        // --- 1. QUARTIER RÉSIDENTIEL (Haut) ---
        // Avant Y=40 -> Maintenant Y=110 (Loin du HUD)
        resid.add(new Building("Route N", "ROUTE", 0, 160));
        resid.add(new Building("Route N", "ROUTE", 100, 160));
        
        Building m1 = new Building("Villa", "MAISON", 20, 110);
        Building m2 = new Building("Pavillon", "MAISON", 120, 110);
        Building jeu = new Building("Parc Enfants", "JEU", 220, 120);

        activeBuildings.add(m1); activeBuildings.add(m2); activeBuildings.add(jeu);
        resid.add(m1); resid.add(m2); resid.add(jeu);

        // --- 2. CENTRE VILLE (Milieu) ---
        // Avant Y=200 -> Maintenant Y=280
        centre.add(new Building("Av. Principale", "ROUTE", 0, 330));
        centre.add(new Building("Av. Principale", "ROUTE", 100, 330));
        centre.add(new Building("Av. Principale", "ROUTE", 200, 330));
        centre.add(new Building("Av. Principale", "ROUTE", 300, 330));

        Building shop1 = new Building("Mall", "COMMERCE", 50, 280);
        Building shop2 = new Building("Boulangerie", "COMMERCE", 350, 280);
        Building statue = new Building("Statue Maire", "MONUMENT", 200, 260); 

        activeBuildings.add(shop1); activeBuildings.add(shop2); activeBuildings.add(statue);
        centre.add(shop1); centre.add(shop2); centre.add(statue);

        // --- 3. ZONE INDUSTRIELLE (Bas) ---
        // Avant Y=350 -> Maintenant Y=430
        Building u1 = new Building("Usine A", "INDUSTRIE", 50, 430);
        Building u2 = new Building("Usine B", "INDUSTRIE", 180, 430);
        Building p1 = new Building("Forêt Tampon", "PARC", 320, 430);

        activeBuildings.add(u1); activeBuildings.add(u2); activeBuildings.add(p1);
        indus.add(u1); indus.add(u2); indus.add(p1);

        cityRoot.add(indus);
        cityRoot.add(centre);
        cityRoot.add(resid);
    }

    private void createHUD() {
        // --- BARRE DE SCORE EN HAUT (DASHBOARD) ---
        
        // Fond semi-transparent pour bien lire le texte
        Rectangle hudBg = new Rectangle(0, 0, 800, 60);
        hudBg.setFill(Color.rgb(0, 0, 0, 0.5)); // Noir à 50% d'opacité

        globalHealthBar = new ProgressBar(1.0);
        globalHealthBar.setPrefWidth(200);
        globalHealthBar.setStyle("-fx-accent: limegreen;");
        
        timerText = new Text("Temps: 0s");
        timerText.setStyle("-fx-font-size: 18px; -fx-fill: white; -fx-font-weight: bold;");
        
        pollutionText = new Text("Pollution: 0%");
        pollutionText.setStyle("-fx-font-size: 14px; -fx-fill: lightgray;");

        // Organisation Horizontale (HBox) au lieu de Verticale pour gagner de la place en hauteur
        HBox hudBox = new HBox(20); // 20px d'espace entre les éléments
        hudBox.setAlignment(Pos.CENTER_LEFT); // Alignement gauche
        hudBox.setLayoutX(20);
        hudBox.setLayoutY(15); // Centré dans la barre de 60px
        hudBox.getChildren().addAll(new Text("Santé Ville:"), globalHealthBar, pollutionText, timerText);
        
        // On stylise le petit texte "Santé Ville"
        ((Text)hudBox.getChildren().get(0)).setStyle("-fx-fill: white; -fx-font-weight: bold;");

        gamePane.getChildren().addAll(hudBg, hudBox);
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // LOGIQUE
                timeElapsed += 0.016;
                for (Building b : activeBuildings) b.increasePollution();

                double totalPol = 0;
                for (Building b : activeBuildings) totalPol += b.getPollutionLevel();
                double avgPol = activeBuildings.isEmpty() ? 0 : totalPol / activeBuildings.size();

                // CONDITIONS FIN
                if (avgPol >= POLLUTION_LIMIT) {
                    gameLoop.stop();
                    GameManager.getInstance().setState(new GameOverState());
                    return;
                }
                if (timeElapsed >= TIME_TO_WIN) {
                    gameLoop.stop();
                    GameManager.getInstance().setState(new VictoryState());
                    return;
                }

                // UI UPDATE
                timerText.setText(String.format("Temps: %.0fs", TIME_TO_WIN - timeElapsed));
                pollutionText.setText(String.format("Pollution: %.0f%% (Max 50)", avgPol));
                
                double sante = 1.0 - (avgPol / 100.0);
                globalHealthBar.setProgress(sante);
                if(sante < 0.5) globalHealthBar.setStyle("-fx-accent: red;");
                else globalHealthBar.setStyle("-fx-accent: limegreen;");

                // VILLE UPDATE
                cityRoot.render(gamePane); 
            }
        };
        gameLoop.start();
    }

    @Override public void handleInput(GameManager context) {}
    @Override public void update() {}
    @Override public void render() {}
}