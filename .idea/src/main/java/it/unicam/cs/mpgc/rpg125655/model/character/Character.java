package it.unicam.cs.mpgc.rpg125655.model.character;

import it.unicam.cs.mpgc.rpg125655.model.item.Item;
import java.util.ArrayList;
import java.util.List;

public class Character {

    private String name;
    private CharacterClass characterClass;
    private CharacterStats stats;
    private List<Item> inventory;

    public Character(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.stats = new CharacterStats(
                characterClass.getBaseHealth(),
                characterClass.getBaseAgility(),
                characterClass.getBaseMagic()
        );
        this.inventory = new ArrayList<>();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public boolean hasItem(String itemId) {
        return inventory.stream().anyMatch(i -> i.getId().equals(itemId));
    }

    public String getName() { return name; }
    public CharacterClass getCharacterClass() { return characterClass; }
    public CharacterStats getStats() { return stats; }
    public List<Item> getInventory() { return inventory; }

    public void setName(String name) { this.name = name; }
    public void setStats(CharacterStats stats) { this.stats = stats; }
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }
}