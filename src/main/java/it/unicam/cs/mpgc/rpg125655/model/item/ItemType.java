package it.unicam.cs.mpgc.rpg125655.model.item;

public enum ItemType {
    WEAPON("Weapon"),
    ARMOR("Armor"),
    POTION("Potion"),
    KEY("Key Item"),
    MISC("Miscellaneous");

    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}