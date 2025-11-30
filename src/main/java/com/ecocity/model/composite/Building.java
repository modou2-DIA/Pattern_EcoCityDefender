package com.ecocity.model.composite;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Building implements CityComponent {
    private String name;
    private String type; // INDUSTRIE, MAISON, PARC, ROUTE, COMMERCE, MONUMENT, JEU
    private double x, y;
    private double currentPollution;
    private double pollutionRate;

    private Group viewGroup; 
    private Rectangle pollutionBarFill;
    private Circle smokeCloud;

    public Building(String name, String type, double x, double y) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        
        switch(type) {
            case "INDUSTRIE": 
                this.currentPollution = 40; 
                this.pollutionRate = 0.20;  
                break;
            case "MAISON":
                this.currentPollution = 10;
                this.pollutionRate = 0.05;
                break;
            case "COMMERCE": // Pollue un tout petit peu
                this.currentPollution = 20;
                this.pollutionRate = 0.08;
                break;
            case "PARC":
            case "JEU": // Aire de jeu aide à dépolluer
                this.currentPollution = 0;
                this.pollutionRate = -0.08; 
                break;
            case "MONUMENT": // Neutre
            default: 
                this.currentPollution = 0;
                this.pollutionRate = 0;
        }
    }

    @Override
    public void render(Pane pane) {
        if (viewGroup == null) createVisuals(pane);
        updateVisuals();
    }

    private void createVisuals(Pane pane) {
        viewGroup = new Group();

        // Dessin selon le type
        switch(type) {
            case "INDUSTRIE": drawFactory(); break;
            case "MAISON": drawHouse(); break;
            case "PARC": drawPark(); break;
            case "ROUTE": drawRoad(); break;
            case "COMMERCE": drawShop(); break;
            case "MONUMENT": drawMonument(); break;
            case "JEU": drawPlayground(); break;
        }

        // Barre de vie (Sauf pour les éléments décoratifs purs ou positifs)
        if (pollutionRate > 0) {
            Rectangle bg = new Rectangle(x, y - 10, 60, 5);
            bg.setFill(Color.RED);
            pollutionBarFill = new Rectangle(x, y - 10, 60, 5);
            pollutionBarFill.setFill(Color.LIMEGREEN);
            viewGroup.getChildren().addAll(bg, pollutionBarFill);
        }
        
        // Nom (pour tous sauf route)
        if(!type.equals("ROUTE")) {
            Text txt = new Text(x, y + 70, name);
            txt.setFont(Font.font(9));
            viewGroup.getChildren().add(txt);
        }

        // Hitbox
        Rectangle hitBox = new Rectangle(x, y-20, 70, 90);
        hitBox.setFill(Color.TRANSPARENT);
        hitBox.setOnMouseClicked(e -> cleanUp());
        viewGroup.getChildren().add(hitBox);

        pane.getChildren().add(viewGroup);
    }

    private void updateVisuals() {
        if (pollutionBarFill != null) {
            double ratio = Math.max(0, 1 - (currentPollution / 100.0));
            pollutionBarFill.setWidth(60 * ratio);
            if (ratio < 0.3) pollutionBarFill.setFill(Color.RED);
            else pollutionBarFill.setFill(Color.LIMEGREEN);
        }
        if (smokeCloud != null) {
            smokeCloud.setOpacity(currentPollution / 100.0);
            smokeCloud.setRadius(10 + (currentPollution / 5.0));
        }
    }

    // --- NOUVEAUX DESSINS ---

    private void drawShop() { // Bâtiment bleu vitré
        Rectangle body = new Rectangle(x, y + 10, 60, 40);
        body.setFill(Color.DODGERBLUE);
        body.setStroke(Color.BLACK);
        Rectangle sign = new Rectangle(x+5, y, 50, 10); // Enseigne
        sign.setFill(Color.YELLOW);
        Rectangle door = new Rectangle(x+25, y+30, 10, 20);
        door.setFill(Color.DARKBLUE);
        viewGroup.getChildren().addAll(body, sign, door);
    }

    private void drawMonument() { // Obélisque/Statue
        Rectangle base = new Rectangle(x+10, y+40, 40, 10);
        base.setFill(Color.GRAY);
        Polygon pillar = new Polygon(x+15, y+40, x+45, y+40, x+30, y-10);
        pillar.setFill(Color.LIGHTGRAY);
        pillar.setStroke(Color.GRAY);
        viewGroup.getChildren().addAll(base, pillar);
    }

    private void drawPlayground() { // Toboggan et sable
        Rectangle sand = new Rectangle(x, y+20, 60, 30);
        sand.setFill(Color.SANDYBROWN);
        Line slide = new Line(x+10, y+10, x+30, y+40);
        slide.setStroke(Color.RED);
        slide.setStrokeWidth(3);
        Circle swing = new Circle(x+50, y+35, 5, Color.ORANGE);
        viewGroup.getChildren().addAll(sand, slide, swing);
    }

    // --- ANCIENS DESSINS ---
    private void drawFactory() {
        Rectangle body = new Rectangle(x, y, 60, 50);
        body.setFill(Color.GRAY); body.setStroke(Color.BLACK);
        Rectangle ch = new Rectangle(x+40, y-20, 15, 30); ch.setFill(Color.DARKRED); ch.setStroke(Color.BLACK);
        smokeCloud = new Circle(x+47, y-25, 10, Color.DARKGRAY); smokeCloud.setOpacity(0);
        viewGroup.getChildren().addAll(ch, body, smokeCloud);
    }
    private void drawHouse() {
        Rectangle body = new Rectangle(x+10, y+20, 40, 30); body.setFill(Color.BEIGE); body.setStroke(Color.BLACK);
        Polygon roof = new Polygon(x, y+20, x+30, y-10, x+60, y+20); roof.setFill(Color.CHOCOLATE);
        viewGroup.getChildren().addAll(body, roof);
    }
    private void drawPark() {
        Rectangle grass = new Rectangle(x, y, 60, 60); grass.setFill(Color.FORESTGREEN);
        Circle tree = new Circle(x+30, y+30, 20, Color.DARKGREEN);
        viewGroup.getChildren().addAll(grass, tree);
    }
    private void drawRoad() {
        Rectangle r = new Rectangle(x, y+25, 80, 20); r.setFill(Color.DARKGRAY);
        Rectangle l = new Rectangle(x, y+34, 80, 2); l.setFill(Color.WHITE); l.getStrokeDashArray().addAll(5d);
        viewGroup.getChildren().addAll(r, l);
    }

    public void cleanUp() { 
        this.currentPollution = Math.max(0, this.currentPollution - 30); 
        updateVisuals(); 
    }
    public void increasePollution() {
        this.currentPollution += pollutionRate;
        if(this.currentPollution > 100) this.currentPollution = 100;
        if(this.currentPollution < 0) this.currentPollution = 0;
    }
    @Override public String getName() { return name; }
    @Override public double getPollutionLevel() { return currentPollution; }
}