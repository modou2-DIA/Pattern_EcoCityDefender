package com.ecocity.model.composite;

import javafx.scene.layout.Pane;

public interface CityComponent {
	
	// On ajoute le paramètre 'pane' pour savoir où dessiner
    void render(Pane pane);
    String getName();
    double getPollutionLevel();
}