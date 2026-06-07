package it.unicam.cs.mpgc.rpg125655.model.character;

public class CharacterStats {

    private int health;
    private int maxHealth;
    private int agility;
    private int magic;
    private int level;
    private int experience;

    public CharacterStats(int health, int agility, int magic) {
        this.maxHealth = health;
        this.health = health;
        this.agility = agility;
        this.magic = magic;
        this.level = 1;
        this.experience = 0;
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    public void heal(int amount) {
        this.health = Math.min(maxHealth, this.health + amount);
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        if (this.experience >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        this.maxHealth += 5;
        this.health = this.maxHealth;
        this.agility++;
        this.magic++;
    }

    public boolean isAlive() { return health > 0; }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAgility() { return agility; }
    public int getMagic() { return magic; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }

    public void setHealth(int health) { this.health = health; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public void setAgility(int agility) { this.agility = agility; }
    public void setMagic(int magic) { this.magic = magic; }
    public void setLevel(int level) { this.level = level; }
    public void setExperience(int experience) { this.experience = experience; }
}