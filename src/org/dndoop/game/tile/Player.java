package org.dndoop.game.tile;

public abstract class Player extends Unit{
    protected int xp;
    protected int level;

    /**
     * Creates a new player, starts at level 1 with 0 xp with the name, stats and position given.
     * @param name
     * @param health
     * @param stats
     * @param character
     * @param position
     */
    public Player(String name, Health health, UnitStats stats, Character character, Position position) {
        super(name, health, stats, character, position);
        this.xp = 0;
        this.level = 1;
    }
    public abstract void onAbilityCast();
    public abstract void onLevelUp();
    public abstract void onDeath();
    /**
     * Levels up the player increasing and resetting its stats accordingly.
     */
    public void levelUp() {
        while(xp>=50*level) {
            xp = xp-50*level;
            level++;
            this.health.levelUp(level);
            this.stats.levelUp(level);
        }
    }
}
