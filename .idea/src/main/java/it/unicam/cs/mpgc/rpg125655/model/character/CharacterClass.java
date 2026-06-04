package it.unicam.cs.mpgc.rpg125655.model.character;

public enum CharacterClass {
    WARRIOR("Warrior", "A strong melee fighter", 10, 3, 1),
    MAGE("Mage", "A powerful spell caster", 5, 1, 8),
    ROGUE("Rogue", "A swift and cunning thief", 6, 5, 3);

    private final String displayName;
    private final String description;
    private final int baseHealth;
    private final int baseAgility;
    private final int baseMagic;

    CharacterClass(String displayName, String description,
                   int baseHealth, int baseAgility, int baseMagic) {
        this.displayName = displayName;
        this.description = description;
        this.baseHealth = baseHealth;
        this.baseAgility = baseAgility;
        this.baseMagic = baseMagic;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public int getBaseHealth() { return baseHealth; }
    public int getBaseAgility() { return baseAgility; }
    public int getBaseMagic() { return baseMagic; }
}