package org.dndoop.game.tile;

import org.dndoop.game.board.GameManager;
import org.dndoop.game.board.GetAtCallback;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.*;

import java.util.Map;

public abstract class Unit extends Tile implements GameEventListener {

    protected String name;
    protected Health health;
    protected UnitStats stats;
    protected String description;//Tal's recommendation TODO implement in all sub classes

    protected Notifier notifier;
    protected GetAtCallback tiles;
    protected Map<GameEventName, EventCallback> events;
    public Unit(String name, Health health, UnitStats stats, Character character, Position position, GameEventNotifier gameEventNotifier)
    {
        super(character, position);
        this.name = name;
        this.health = health;
        this.stats = stats;

        this.notifier = (GameEvent e) -> gameEventNotifier.notify(e);
    }

    /**
     * Just sends your request to move somewhere/interact with something using a direction.
     * @param direction the direction you're trying to move to.
     */
    protected void move(Direction direction) {
        tiles.getAt(position.move(direction)).accept(this);
    }

    public void setTilesAccess(GetAtCallback callback) {
        this.tiles = callback;
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

    /**Reactions for events*/
    public abstract void buildMapEvents();
    /**Game tick*/
    public abstract void onTick();
}
