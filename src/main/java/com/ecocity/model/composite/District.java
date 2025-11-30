package com.ecocity.model.composite;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class District implements CityComponent {
    private String name;
    private List<CityComponent> components = new ArrayList<>();

    public District(String name) {
        this.name = name;
    }

    public void add(CityComponent component) {
        components.add(component);
    }

    public void remove(CityComponent component) {
        components.remove(component);
    }

    @Override
    public void render(Pane pane) {
        // Délègue le dessin à chaque enfant (Pattern Composite)
        for (CityComponent c : components) {
            c.render(pane);
        }
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getPollutionLevel() {
        return components.stream().mapToDouble(CityComponent::getPollutionLevel).sum();
    }
}