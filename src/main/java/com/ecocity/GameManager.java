package com.ecocity;

import com.ecocity.model.state.GameState;
import com.ecocity.model.state.MenuState;
import javafx.scene.layout.BorderPane;

// Singleton pour gérer le jeu
public class GameManager {
    private static GameManager instance;
    private GameState currentState;
    private BorderPane root; // Racine de l'UI JavaFX

    private GameManager() {
        root = new BorderPane();
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void initialize() {
        // État initial = Menu
        setState(new MenuState(this));
    }

    public void setState(GameState state) {
        this.currentState = state;
        this.currentState.render(); // Déclenche le changement visuel
    }

    public BorderPane getRoot() { return root; }
}