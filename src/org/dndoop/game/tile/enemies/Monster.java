package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.GameRandomizer;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;

import java.util.HashMap;

public class Monster extends Enemy {

    private int range;

    public Monster(String name, Health health, UnitStats stats,
                   Character character, Position position, int experience, int range,
                   GameEventNotifier gameEventNotifier) {
        super(name, health, stats, character, position, experience, gameEventNotifier);

        //Add legality check on this: TODO
        this.range = range;
        this.events = new HashMap<>();

        GameEventNotifier.getInstance().addListener(this);

    }

    @Override
    public void buildMapEvents(){
        events.put(GameEventName.PLAYER_ACTION_EVENT, (GameEvent event) -> {
            onTick();
            if(event.isPlayerEvent() && position.range(event.getPosition()) <= range) {
                playerInRange(event.getActor());
            } else {
                randomMove();
            }
        });
        events.put(GameEventName.PLAYER_ABILITY_CAST_EVENT, (GameEvent event) -> {
            /*...Might be deprecated*/
            events.get(GameEventName.PLAYER_ACTION_EVENT).execute(event);
        });
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if(events.containsKey(event.getName())) {
            events.get(event.getName()).execute(event);
        }
    }

    /**
     * Used only when player is in range onGameEvent, handles the movement.
     * @param player the player, must check if that's really a player instance using 'isPlayerEvent()'.
     */
    public void playerInRange(Unit player){
        Position playerPos = player.getPosition();
        int dx = position.getX() - playerPos.getX();
        int dy = position.getY() - playerPos.getY();

        //This is disgusting, but it is what it is.
        if(Math.abs(dx) > Math.abs(dy)) {
            if(dx > 0) {
                move(Direction.LEFT);
            } else {
                move(Direction.RIGHT);
            }
        } else {
            if(dy > 0) {
                move(Direction.UP);
            } else {
                move(Direction.DOWN);
            }
        }
    }

    /**
     * Used when player isn't in range.
     */
    public void randomMove() {
        Direction[] DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        int direction = GameRandomizer.getInstance().getRandomInt(1, 4);
        move(DIRECTIONS[direction]);
    }

    @Override
    public void visit(Empty empty) {
        position.swapPositions(empty.getPosition());
    }

    @Override
    public void visit(Player player) {
        //Combat TODO...
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onDeath() {
        notifier.notify(new GameEvent(GameEventName.ENEMY_DEATH_EVENT, position, this));
    }


    /**
     * Rolls up a damage amount between 0-attackPoints
     */
    @Override
    public void attack() {
        //Roll attack TODO
    }

    /**
     * Rolls up a defence amount between 0-defensePoints
     */
    @Override
    public void defend() {
        //Roll defence TODO
    }
}
