package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;
import java.lang.String;

public abstract class Player extends Unit {

    private static int INITIAL_XP = 0;
    private static final Character PLAYER_CHARACTER = '@';

    protected int level;

    /**
     * Creates a new player, starts at level 1 with 0 xp with the name, stats and position given.
     * @param name
     * @param health
     * @param attack
     * @param defense
     * @param position
     * @param gameEventNotifier
     */
    public Player(
            String name, int health, int attack, int defense, Position position, GameEventNotifier gameEventNotifier
    ) {
        super(name, health, attack, defense, PLAYER_CHARACTER, INITIAL_XP, position, gameEventNotifier);
        this.level = 1;
    }

    @Override
    public void buildMapEvents() {
        events.put(GameEventName.ENEMY_DEATH_EVENT, (GameEvent event) -> {
            gainXp(event.getActor().getXp());
        });
    }

    @Override
    public void onTick() {
        notifier.notify(new GameEvent(GameEventName.PLAYER_ACTION_EVENT, position, this));
    }

    /**
     * Visitor design pattern, visits the pattern later.
     * @param unit self.
     */
    public void accept(Unit unit){
        unit.visit(this);
    }

    @Override
    public void visit(Enemy enemy) {
        attack(enemy);
    }

    /**
     * The only 2 impl of visitor in the abstract class of player
     */
    @Override
    public void visit(Empty empty) {
        position.swapPositions(empty.getPosition());
    }
    /**
     * The only 2 impl of visitor in the abstract class of player
     */
    @Override
    public void visit(Player player) {
        //If this function gets invoked you f*****-up really hard buddy!
    }

    @Override
    public void onDeath() {
        this.character = 'X';
        notifier.notify(new GameEvent(GameEventName.PLAYER_DIED_EVENT, position, this));
        m.send("Game Over");
    }

    public abstract void castAbility();
    public abstract void onAbilityCast();
    public abstract void onLevelUp();


    /**
     * Adding xp to it's bar and taking care of leveling-up in the correct scenario.
     * @param gain The xp that was gained/ to be added.
     */
    public void gainXp(int gain) {
        this.xp += gain;
        levelUp();
    }

    /**
     * Levels up the player increasing and resetting its stats accordingly.
     */
    public void levelUp() {
        while(xp>=50*level) {
            xp = xp-50*level;
            level++;
            this.health.levelUp(level);
            this.stats.levelUp(level);
            onLevelUp();
        }
    }

    @Override
    public String getDescription(){
        String description = super.getDescription();
        description += fixedLengthString("Level: "+level);
        description += fixedLengthString("Experience: "+xp+"/"+50*level);
        return description;
    }

    public void getBar(String s) {
        m.send(getDescription());
    }
}
