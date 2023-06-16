package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEventListener;
import org.dndoop.game.utils.events.GameEventNotifier;

public abstract class Enemy extends Unit {

    protected int experience;

    public Enemy(
            String name, Health health, UnitStats stats,
            Character character, Position position, int experience, GameEventNotifier gameEventNotifier) {
        super(name, health, stats, character, position, gameEventNotifier);
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
