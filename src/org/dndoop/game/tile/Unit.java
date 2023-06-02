package org.dndoop.game.tile;

import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.PlayerEventListener;

public abstract class Unit extends Tile implements PlayerEventListener {

    protected String name;
    protected Health health;
    protected UnitStats stats;
    public Unit(String name, Health health, UnitStats stats, Character character, Position position)
    {
        super(character, position);
        this.name = name;
        this.health = health;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public UnitStats getStats() {
        return stats;
    }

    public void setStats(UnitStats stats) {
        this.stats = stats;
    }

    public abstract void onDeath();
}
