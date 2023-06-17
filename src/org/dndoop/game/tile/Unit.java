package org.dndoop.game.tile;

import org.dndoop.game.board.GameManager;
import org.dndoop.game.board.GetAtCallback;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.GameRandomizer;
import org.dndoop.game.utils.events.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Unit extends Tile implements GameEventListener {

    protected int xp;
    protected String name;
    protected Health health;
    protected UnitStats stats;
    protected String description;//Tal's recommendation TODO implement in all sub classes

    protected Notifier notifier;
    protected GetAtCallback tiles;
    protected Map<GameEventName, EventCallback> events;
    public Unit(String name, Health health, UnitStats stats, Character character,
                int xp, Position position, GameEventNotifier gameEventNotifier)
    {
        super(character, position);
        this.name = name;
        this.health = health;
        this.stats = stats;
        this.xp = xp;

        this.notifier = (GameEvent e) -> gameEventNotifier.notify(e);
        this.events = new HashMap<>();
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if(events.containsKey(event.getName())) {
            events.get(event.getName()).execute(event);
        }
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

    /**
     * Rolls up a damage amount between 0-attackPoints and attacks target.
     * @param target Unit to be attacked.
     */
    public void attack(Unit target) {
        int attackDamage = GameRandomizer.getInstance().getRandomInt(0, getStats().getAttackPoints());
        target.defend(attackDamage);
    }

    /**
     * Damaging the unit with giving it the ability to defend.
     * Rolls up a defence amount between 0-defensePoints
     */
    public void defend(int attackDamage) {
        int defenseAttack = GameRandomizer.getInstance().getRandomInt(0, getStats().getDefensePoints());
        int damage = attackDamage - defenseAttack;
        if(damage >= 0) {
            if(getHealth().damage(damage)) {
                onDeath();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getXp() {
        return xp;
    }

    public Health getHealth() {
        return health;
    }
    public boolean isAlive() {
        return getHealth().getHealthAmount() > 0;
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

    public abstract void visit(Empty empty);
    public abstract void visit(Enemy enemy);
    public abstract void visit(Player player);

    /**Reactions for events*/
    public abstract void buildMapEvents();
    /**Game tick*/
    public abstract void onTick();
}
