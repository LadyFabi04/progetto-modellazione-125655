package it.unicam.cs.mpgc.rpg125655.model;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import java.time.LocalDateTime;
import java.util.UUID;

// Rappresenta lo stato corrente di una partita salvata.
public class GameState {

    private final String    saveId;
    private final Character player;
    private final Story     story;
    private LocalDateTime   lastSaved;

    // Costruisce un nuovo GameState generando un id univoco.
    public GameState(Character player, Story story) {
        this.saveId    = UUID.randomUUID().toString();
        this.player    = player;
        this.story     = story;
        this.lastSaved = LocalDateTime.now();
    }

    // Costruisce un GameState con id e data di salvataggio specificati.
    public GameState(String saveId, Character player,
                     Story story, LocalDateTime lastSaved) {
        this.saveId    = saveId;
        this.player    = player;
        this.story     = story;
        this.lastSaved = lastSaved;
    }

    public String    getSaveId()    { return saveId; }
    public Character getPlayer()    { return player; }
    public Story     getStory()     { return story; }
    public LocalDateTime getLastSaved() { return lastSaved; }

    // Aggiorna la data dell'ultimo salvataggio.
    public void updateLastSaved() {
        this.lastSaved = LocalDateTime.now();
    }
}