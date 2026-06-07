package it.unicam.cs.mpgc.rpg125655;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.character.CharacterClass;
import it.unicam.cs.mpgc.rpg125655.model.story.*;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg125655.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    // Motore di gioco usato nei test.
    private GameEngine engine;

    // Personaggio di test.
    private Character personaggio;

    // Inizializza una storia minimale prima di ogni test.
    @BeforeEach
    void setUp() {
        Choice scelta = new Choice("c1", "Vai avanti", "secondo", null, null);
        StoryNode primo = new StoryNode("primo", "Inizio", "Sei all'inizio.", List.of(scelta), false, null);
        StoryNode secondo = new StoryNode("secondo", "Fine", "Sei arrivato.", List.of(), true, "good");

        Map<String, StoryNode> nodes = new HashMap<>();
        nodes.put("primo", primo);
        nodes.put("secondo", secondo);

        Story story = new Story("test", "Test", "primo", nodes);

        personaggio = new Character("Eroe", CharacterClass.WARRIOR);

        RequirementChecker checker = new RequirementChecker();
        ConsequenceApplier applier = new ConsequenceApplier();
        JsonSaveManager saveManager = new JsonSaveManager();
        engine = new GameEngine(checker, applier, saveManager);
        engine.startNewGame(personaggio, story);
    }

    // Verifica che il nodo corrente all'avvio sia quello iniziale.
    @Test
    void testNodoIniziale() {
        assertEquals("primo", engine.getCurrentNode().getId());
    }

    // Verifica che applicare una scelta valida cambi il nodo corrente.
    @Test
    void testNavigazioneScelta() {
        Choice scelta = engine.getAvailableChoices().get(0);
        engine.makeChoice(scelta);
        assertEquals("secondo", engine.getCurrentNode().getId());
    }

    // Verifica che il gioco riconosca correttamente la fine della storia.
    @Test
    void testFineGioco() {
        assertFalse(engine.isGameOver());
        Choice scelta = engine.getAvailableChoices().get(0);
        engine.makeChoice(scelta);
        assertTrue(engine.isGameOver());
    }

    // Verifica che le scelte disponibili siano quelle attese nel nodo iniziale.
    @Test
    void testScelteDisponibili() {
        List<Choice> scelte = engine.getAvailableChoices();
        assertEquals(1, scelte.size());
        assertEquals("Vai avanti", scelte.get(0).getText());
    }
}