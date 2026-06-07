package it.unicam.cs.mpgc.rpg125655.service;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.item.Item;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;

// Applica le conseguenze di una scelta al personaggio.
public class ConsequenceApplier {

    // Applica salute, esperienza e oggetti al personaggio in base alla conseguenza.
    public void apply(Choice choice, Character player) {
        Choice.Consequence cons = choice.getConsequence();
        if (cons == null) return;

        // Modifica salute
        if (cons.getHealthDelta() != 0) {
            if (cons.getHealthDelta() > 0) {
                player.getStats().heal(cons.getHealthDelta());
            } else {
                player.getStats().takeDamage(-cons.getHealthDelta());
            }
        }

        // Oggetti rimossi
        for (String itemId : cons.getItemsRemoved()) {
            player.getInventory().stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst()
                    .ifPresent(player::removeItem);
        }
    }
}