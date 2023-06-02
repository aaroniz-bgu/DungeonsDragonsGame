package org.dndoop.game.tile;

public abstract class Unit extends Tile{

    private String name;
    private Health health;
    private UnitStats stats;
    public Unit(String name, Health health, UnitStats stats, Character character, Position position)
    {
        super(character, position);
        this.name = name;
        this.health = health;
        this.stats = stats;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Health getHealth() {
        return health;
    }

    protected void setHealth(Health health) {
        this.health = health;
    }

    protected UnitStats getStats() {
        return stats;
    }

    protected void setStats(UnitStats stats) {
        this.stats = stats;
    }
}
