package it.unicam.cs.mpgc.rpg125655.ui;

import javafx.application.Application;
import javafx.stage.Stage;

// Punto di ingresso dell'applicazione JavaFX.
public class MainApp extends Application {

    private static Stage primaryStage;

    // Metodo di avvio chiamato da JavaFX all'apertura dell'applicazione.
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Chronicles of Fate");
        primaryStage.setWidth(900);
        primaryStage.setHeight(650);
        primaryStage.setResizable(false);

        SceneManager.initialize(primaryStage);
        SceneManager.showMainMenu();

        primaryStage.show();
    }

    // Restituisce lo Stage principale dell'applicazione.
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    // Metodo main: lancia l'applicazione JavaFX.
    public static void main(String[] args) {
        launch(args);
    }
}