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
import org.dndoop.game.utils.MessageCallback;
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
    protected MessageCallback m;
    protected GetAtCallback tiles;
    protected Map<GameEventName, EventCallback> events;
    private static final int FIXED_STRING_LENGTH = 30;
    public Unit(String name, int healthPool, int attack, int defense, Character character,
                int xp, Position position, GameEventNotifier gameEventNotifier)
    {
        super(character, position);
        Health health = new Health(healthPool);
        UnitStats stats = new UnitStats(attack, defense);
        this.name = name;
        this.health = health;
        this.stats = stats;
        this.xp = xp;
        this.notifier = (GameEvent e) -> gameEventNotifier.notify(e);
        this.events = new HashMap<>();

        buildMapEvents();
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
    public void move(Direction direction) {
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
        m.send(getName()+" engaged in combat with "+target.getName()+".");
        m.send(getDescription());
        m.send(target.getDescription());

        int attackDamage = GameRandomizer.getInstance().getRandomInt(0, getStats().getAttackPoints());
        m.send(getName()+" rolled "+attackDamage+" attack points.");

        target.defend(attackDamage, this);
    }

    /**
     * Damaging the unit with giving it the ability to defend.
     * Rolls up a defence amount between 0-defensePoints
     */
    public void defend(int attackDamage, Unit attacker) {

        int defensePoints = GameRandomizer.getInstance().getRandomInt(0, getStats().getDefensePoints());
        int damage = attackDamage - defensePoints;

        m.send(getName()+" rolled "+defensePoints+" defense points.");

        if(damage >= 0) {
            m.send(attacker.getName()+" dealt "+damage+" damage to "+getName()+".");
            if(getHealth().damage(damage)) {
                onDeath(attacker);
            }
        }
    }

    public void setMessageCallback(MessageCallback m) {
        this.m = m;
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


    public abstract void onDeath(Unit attacker);

    public abstract void visit(Empty empty);
    public abstract void visit(Enemy enemy);
    public abstract void visit(Player player);
    /**Reactions for events*/
    public abstract void buildMapEvents();
    /**Game tick*/
    public abstract void onTick();

    public String getDescription() {
        String description = "";
        description += fixedLengthString(name);
        description += fixedLengthString("Health: "+health.toString());
        description += fixedLengthString("Attack: "+stats.getAttackPoints());
        description += fixedLengthString("Defense: "+stats.getDefensePoints());
        //TODO add new line here if needed
        return description;
    }

    /**
     * Creates a string with a fixed length ({@value FIXED_STRING_LENGTH}), helps with creating nicely formatted
     * outputs.
     * @param string
     * @return
     */
    protected String fixedLengthString(String string) {
        return String.format("%1$-"+FIXED_STRING_LENGTH+ "s", string);
    }

    /**
     * Creates a string with a fixed length, helps with creating nicely formatted
     * outputs.
     * @param string
     * @param length
     * @return
     */
    protected String fixedLengthString(String string, int length) {
        return String.format("%1$-"+length+ "s", string);
    }
}
