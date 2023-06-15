package org.dndoop.game.tile;

import org.dndoop.game.board.GameManager;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;

public abstract class Unit extends Tile implements GameEventListener {

    protected String name;
    protected Health health;
    protected UnitStats stats;
    protected String description;//Tal's recommendation TODO implement in all sub classes
    public Unit(String name, Health health, UnitStats stats, Character character, Position position)
    {
        super(character, position);
        this.name = name;
        this.health = health;
        this.stats = stats;
    }

    protected void move(Direction direction) {
        GameManager.getInstance()
                .getGameBoard()
                .getAt(position.move(direction))
                .accept(this);
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

    /**
     * Rolls up a damage amount between 0-attackPoints
     */
    public abstract void attack();

    /**
     * Rolls up a defence amount between 0-defensePoints
     */
    public abstract void defend();


    public abstract void visit(Empty empty);
    public abstract void visit(Enemy enemy);
    public abstract void visit(Player player);
}
