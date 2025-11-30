package com.ecocity.model.factory;

import com.ecocity.utils.GameLogger;

public class PollutionFactory {
    
    public static Object createPollutionSource(String type) {
        GameLogger.getInstance().log("FACTORY", "Creating enemy type: " + type);
        
        switch (type.toLowerCase()) {
            case "smog":
                return new String("Nuage de Smog"); // Remplacer par classe concrète
            case "waste":
                return new String("Déchets toxiques");
            default:
                throw new IllegalArgumentException("Type inconnu");
        }
    }
}