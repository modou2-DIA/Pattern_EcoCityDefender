package com.ecocity.model.state;

import com.ecocity.GameManager;

// State Pattern Interface [cite: 47]
public interface GameState {
    void handleInput(GameManager context);
    void update();
    void render();
}