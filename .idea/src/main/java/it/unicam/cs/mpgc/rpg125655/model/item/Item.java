package it.unicam.cs.mpgc.rpg125655.model.item;

public class Item {

    private String id;
    private String name;
    private String description;
    private ItemType type;

    public Item(String id, String name, String description, ItemType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ItemType getType() { return type; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setType(ItemType type) { this.type = type; }
}