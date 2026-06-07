package it.unicam.cs.mpgc.rpg125655;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.character.CharacterClass;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;
import it.unicam.cs.mpgc.rpg125655.service.RequirementChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementCheckerTest {

    private RequirementChecker checker;

    // Personaggio di test.
    private Character personaggio;

    @BeforeEach
    void setUp() {
        checker = new RequirementChecker();
        personaggio = new Character("Eroe", CharacterClass.WARRIOR);
    }

    // Verifica che una scelta senza requisiti sia sempre disponibile.
    @Test
    void testNessunoRequisito() {
        Choice scelta = new Choice("c1", "Vai", "nodo2", null, null);
        assertTrue(checker.isMet(scelta, personaggio));
    }

    // Verifica che un requisito di agilità soddisfatto ritorni true.
    @Test
    void testRequisitoAgilitaSoddisfatto() {
        Choice.Requirement req = new Choice.Requirement(null, null, 1, null, null);
        Choice scelta = new Choice("c1", "Vai", "nodo2", req, null);
        assertTrue(checker.isMet(scelta, personaggio));
    }

    // Verifica che un requisito di agilità non soddisfatto ritorni false.
    @Test
    void testRequisitoAgilitaNonSoddisfatto() {
        Choice.Requirement req = new Choice.Requirement(null, null, 999, null, null);
        Choice scelta = new Choice("c1", "Vai", "nodo2", req, null);
        assertFalse(checker.isMet(scelta, personaggio));
    }

    // Verifica che un requisito su oggetto non posseduto ritorni false.
    @Test
    void testRequisitoOggettoMancante() {
        Choice.Requirement req = new Choice.Requirement(null, null, null, null, "spada_magica");
        Choice scelta = new Choice("c1", "Vai", "nodo2", req, null);
        assertFalse(checker.isMet(scelta, personaggio));
    }
}