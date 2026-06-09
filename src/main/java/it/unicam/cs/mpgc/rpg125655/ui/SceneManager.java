package it.unicam.cs.mpgc.rpg125655.ui;

import it.unicam.cs.mpgc.rpg125655.model.GameState;
import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonStoryLoader;
import it.unicam.cs.mpgc.rpg125655.ui.view.CharacterCreationView;
import it.unicam.cs.mpgc.rpg125655.ui.view.GameView;
import it.unicam.cs.mpgc.rpg125655.ui.view.MainMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Gestore centralizzato delle schermate dell'applicazione.
public class SceneManager {

    private static Stage stage;

    // Inizializza il SceneManager con lo Stage principale.
    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
    }

    // Mostra la schermata del menu principale.
    public static void showMainMenu() {
        MainMenuView view = new MainMenuView();
        Scene scene = new Scene(view.getRoot());
        scene.getStylesheets().add(SceneManager.class.getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    // Mostra la schermata di creazione del personaggio.
    public static void showCharacterCreation() {
        CharacterCreationView view = new CharacterCreationView();
        Scene scene = new Scene(view.getRoot());
        scene.getStylesheets().add(SceneManager.class.getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    // Mostra la schermata di gioco con il personaggio e la storia forniti.
    public static void showGame(Character character, Story story) {
        GameView view = new GameView(character, story);
        Scene scene = new Scene(view.getRoot());
        scene.getStylesheets().add(SceneManager.class.getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    // Mostra la schermata di gioco caricando uno stato salvato.
    public static void showGameFromSave(GameState stato) {
        try {
            JsonStoryLoader loader = new JsonStoryLoader("/data/chronicles_of_fate.json");
            Story storia = loader.load();
            GameView view = new GameView(stato, storia);
            Scene scene = new Scene(view.getRoot());
            scene.getStylesheets().add(SceneManager.class.getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}