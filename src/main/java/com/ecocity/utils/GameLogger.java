package com.ecocity.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.time.LocalDateTime;

// Singleton Pattern pour le Logger
public class GameLogger {
    private static GameLogger instance;
    private Logger logger;
    private FileHandler fileHandler;

    private GameLogger() {
        try {
            logger = Logger.getLogger("EcoCityLog");
            // Création du fichier log comme demandé dans la section 2.4.2 [cite: 86]
            fileHandler = new FileHandler("game_trace.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Pas de log dans la console brute
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }

    public void log(String type, String message) {
        // Format demandé: [TIMESTAMP] [TYPE] Message
        String logMsg = String.format("[%s] [%s] %s", LocalDateTime.now(), type, message);
        System.out.println(logMsg); // Affiche aussi dans la console
        logger.info(logMsg);
    }
}