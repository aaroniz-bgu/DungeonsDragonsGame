package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.tile_utils.UnitStats;

public abstract class Enemy extends Unit {

    protected int experience;

    public Enemy(
            String name, Health health, UnitStats stats,
            Character character, Position position, int experience) {
        super(name, health, stats, character, position);
        this.experience = experience;
    }

    /**
     * Visitor design pattern, visits the pattern later.
     * @param unit self.
     */
    public void accept(Unit unit) {
        unit.visit(this);
    }

    /**
     * Enemies that meet should not do anything...
     */
    @Override
    public void visit(Enemy enemy) {
        //Do nothing...
    }

    @Override
    public abstract void onDeath();
}
