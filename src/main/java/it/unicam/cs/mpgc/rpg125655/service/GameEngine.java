package it.unicam.cs.mpgc.rpg125655.service;

import it.unicam.cs.mpgc.rpg125655.model.GameState;
import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import it.unicam.cs.mpgc.rpg125655.model.story.StoryNode;
import it.unicam.cs.mpgc.rpg125655.persistence.SaveManager;

import java.io.IOException;
import java.util.List;

// Motore centrale del gioco

public class GameEngine {

    private final RequirementChecker requirementChecker;
    private final ConsequenceApplier consequenceApplier;
    private final SaveManager        saveManager;
    private       GameState          currentState;

    public GameEngine(RequirementChecker requirementChecker,
                      ConsequenceApplier consequenceApplier,
                      SaveManager saveManager) {
        this.requirementChecker = requirementChecker;
        this.consequenceApplier = consequenceApplier;
        this.saveManager        = saveManager;
    }

    // Avvia una nuova partita con il personaggio e la storia forniti.
    public void startNewGame(Character player, Story story) {
        player.setCurrentNodeId(story.getStartNodeId());
        this.currentState = new GameState(player, story);
    }

    // Restituisce il nodo corrente della storia.
    public StoryNode getCurrentNode() {
        return currentState.getStory()
                .getNode(currentState.getPlayer().getCurrentNodeId());
    }

    // Restituisce le scelte disponibili per il giocatore corrente.
    public List<Choice> getAvailableChoices() {
        return getCurrentNode().getChoices().stream()
                .filter(c -> requirementChecker.isMet(c, currentState.getPlayer()))
                .toList();
    }

    // Esegue la scelta selezionata e sposta il giocatore al nodo successivo.
    public void makeChoice(Choice choice) {
        consequenceApplier.apply(choice, currentState.getPlayer());
        currentState.getPlayer().setCurrentNodeId(choice.getTargetNodeId());
    }

    // Restituisce true se il nodo corrente è un finale.
    public boolean isGameOver() {
        return getCurrentNode().isEnding();
    }

    // Salva lo stato corrente della partita.
    public void saveGame() throws IOException {
        saveManager.save(currentState);
    }

    // Restituisce lo stato corrente della partita.
    public GameState getCurrentState() {
        return currentState;
    }

    // Imposta uno stato di gioco caricato da un salvataggio.
    public void loadState(GameState state) {
        this.currentState = state;
    }
}