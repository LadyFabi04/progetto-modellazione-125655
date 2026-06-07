package it.unicam.cs.mpgc.rpg125655.service;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;

// Verifica se i requisiti di una scelta sono soddisfatti dal personaggio.
public class RequirementChecker {

    // Restituisce true se il personaggio soddisfa i requisiti della scelta.
    public boolean isMet(Choice choice, Character player) {
        Choice.Requirement req = choice.getRequirement();
        if (req == null) return true;
        if (req.getMinAgility() != null
                && player.getStats().getAgility() < req.getMinAgility()) {
            return false;
        }
        if (req.getRequiredItemId() != null
                && !player.hasItem(req.getRequiredItemId())) {
            return false;
        }
        return true;
    }
}