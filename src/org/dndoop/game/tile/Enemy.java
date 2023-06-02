package org.dndoop.game.tile;

public abstract class Enemy extends Unit {

    protected int experience;

    public Enemy(
            String name, Health health, UnitStats stats,
            Character character, Position position, int experience) {
        super(name, health, stats, character, position);
        this.experience = experience;
    }

    @Override
    public abstract void onTick();
    @Override
    public abstract void onDeath();
}
