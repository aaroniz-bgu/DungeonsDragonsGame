package org.dndoop.game.tile;

public abstract class Unit extends Tile{

    protected String name;
    protected Health health;
    protected UnitStats stats;

    public Unit(String name, Health health, UnitStats stats, Character character, Position position)
    {
        this.position = position;
        this.character = character;
    }
}
