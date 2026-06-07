package it.unicam.cs.mpgc.rpg125655.ui.view;

import it.unicam.cs.mpgc.rpg125655.model.GameState;
import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg125655.service.ConsequenceApplier;
import it.unicam.cs.mpgc.rpg125655.service.GameEngine;
import it.unicam.cs.mpgc.rpg125655.service.RequirementChecker;
import it.unicam.cs.mpgc.rpg125655.ui.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

// Schermata principale di gioco: mostra il testo narrativo e le scelte disponibili.
public class GameView {

    private final VBox root;

    private final GameEngine engine;

    // Area dove vengono mostrate le scelte.
    private final VBox areaScelte;

    // Testo narrativo del nodo corrente.
    private final Text testoNarrativo;

    // Costruisce la schermata di gioco con il personaggio e la storia forniti.
    public GameView(Character personaggio, Story storia) {
        RequirementChecker checker = new RequirementChecker();
        ConsequenceApplier applier = new ConsequenceApplier();
        JsonSaveManager saveManager = new JsonSaveManager();
        engine = new GameEngine(checker, applier, saveManager);
        engine.startNewGame(personaggio, storia);

        root = new VBox(15);
        root.getStyleClass().add("root");
        root.setPadding(new Insets(30));

        Text intestazione = new Text(personaggio.getName() + " — " + personaggio.getCharacterClass().getDisplayName());
        intestazione.getStyleClass().add("subtitle");

        // Area testo narrativo
        testoNarrativo = new Text();
        testoNarrativo.getStyleClass().add("narrative");
        testoNarrativo.setWrappingWidth(820);

        TextFlow flussoTesto = new TextFlow(testoNarrativo);
        flussoTesto.getStyleClass().add("narrative-box");

        ScrollPane scroll = new ScrollPane(flussoTesto);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(300);
        scroll.getStyleClass().add("scroll-pane");

        // Area scelte
        areaScelte = new VBox(10);
        areaScelte.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(intestazione, scroll, areaScelte);

        aggiornaPagina();
    }

    // Aggiorna il testo e le scelte in base al nodo corrente.
    private void aggiornaPagina() {
        testoNarrativo.setText(engine.getCurrentNode().getNarrativeText());
        areaScelte.getChildren().clear();

        if (engine.isGameOver()) {
            Text fine = new Text("— Fine —");
            fine.getStyleClass().add("subtitle");

            Button btnMenu = new Button("Torna al Menu");
            btnMenu.getStyleClass().add("btn-primary");
            btnMenu.setOnAction(e -> SceneManager.showMainMenu());

            areaScelte.getChildren().addAll(fine, btnMenu);
            return;
        }

        List<Choice> scelte = engine.getAvailableChoices();
        for (int i = 0; i < scelte.size(); i++) {
            Choice scelta = scelte.get(i);
            Button btnScelta = new Button(scelta.getText());
            btnScelta.getStyleClass().add("btn-choice");
            btnScelta.setMaxWidth(Double.MAX_VALUE);
            btnScelta.setOnAction(e -> {
                engine.makeChoice(scelta);
                aggiornaPagina();
            });
            areaScelte.getChildren().add(btnScelta);
        }
    }

    // Restituisce il nodo radice della schermata.
    public VBox getRoot() {
        return root;
    }
}