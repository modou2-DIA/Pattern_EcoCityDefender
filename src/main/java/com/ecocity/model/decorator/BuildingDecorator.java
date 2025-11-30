package com.ecocity.model.decorator;

import com.ecocity.model.composite.CityComponent;
import com.ecocity.utils.GameLogger;
import javafx.scene.layout.Pane;

public abstract class BuildingDecorator implements CityComponent {
    protected CityComponent decoratedBuilding;

    public BuildingDecorator(CityComponent building) {
        this.decoratedBuilding = building;
        GameLogger.getInstance().log("DECORATOR", "Applied to " + building.getName());
    }

    @Override
    public void render(Pane pane) {
        decoratedBuilding.render(pane);
    }
    
    @Override
    public String getName() { return decoratedBuilding.getName(); }
}