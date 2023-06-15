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
import org.dndoop.game.utils.events.GameEventNotifier;

public class Monster extends Enemy {

    private int range;

    public Monster(String name, Health health, UnitStats stats,
                   Character character, Position position, int experience, int range) {
        super(name, health, stats, character, position, experience);

        //Add legality check on this: TODO
        this.range = range;

        GameEventNotifier.getInstance().addListener(this);
    }

    @Override
    public void onTick(GameEvent event) {
        if(event.isPlayerEvent() && position.range(event.getPosition()) <= range) {
            playerInRange(event.getActor());
        } else {
            randomMove();
        }
    }

    /**
     * Used only when player is in range onTick, handles the movement.
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
        int move = GameRandomizer.getInstance().getRandomInt(1, 4);
        switch (move) {
            case 1 -> move(Direction.DOWN);
            case 2 -> move(Direction.UP);
            case 3 -> move(Direction.LEFT);
            case 4 -> move(Direction.RIGHT);
        }
    }

    @Override
    public void visit(Empty empty) {
        position.swapPositions(empty.getPosition());
    }

    @Override
    public void visit(Enemy enemy) {
        //Do nothing...
    }

    @Override
    public void visit(Player player) {
        //Combat TODO...
    }

    @Override
    public void onDeath() {
        GameEventNotifier.getInstance().removeListener(this);
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
