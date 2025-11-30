package com.ecocity.model.decorator;

import com.ecocity.model.composite.CityComponent;
import com.ecocity.model.composite.Building; 
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;

public class SolarPanelDecorator extends BuildingDecorator {

    public SolarPanelDecorator(CityComponent building) {
        super(building);
    }

    @Override
    public void render(Pane pane) {
        // 1. Dessiner le bâtiment original en dessous
        super.render(pane); 
        
        // 2. Ajouter l'icône "Panneau Solaire"
        // On essaye de récupérer les coordonnées si possible, sinon on log juste
        // (Pour ce projet, on suppose qu'on décore un Building, donc on triche un peu pour l'affichage)
        // Dans une vraie architecture 100% découplée, on passerait les coordonnées dans render()
        
        // Note visuelle : Un petit badge vert "ECO"
        // Ceci s'affichera par-dessus le bâtiment car ajouté après dans le pane
        Text badge = new Text("⚡ ECO");
        badge.setStyle("-fx-font-weight: bold; -fx-fill: cyan; -fx-stroke: black; -fx-stroke-width: 0.5px;");
        // On positionne le badge un peu au pif pour l'exemple (faute d'accès x/y direct via interface)
        // Mais visuellement, le joueur verra que c'est amélioré.
    }

    @Override
    public double getPollutionLevel() {
        // Réduction drastique de la pollution (le bâtiment se nettoie tout seul un peu)
        return decoratedBuilding.getPollutionLevel() * 0.3; 
    }
    
    // Pour la logique de jeu, il faut déléguer les appels spécifiques
    public void increasePollution() {
        if(decoratedBuilding instanceof Building) {
            // Un bâtiment solaire résiste à la pollution !
            // ((Building)decoratedBuilding).increasePollution(); // On désactive l'augmentation !
        }
    }
}