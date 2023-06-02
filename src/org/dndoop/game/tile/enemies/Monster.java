package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.GameRandomizer;
import org.dndoop.game.utils.events.PlayerEvent;
import org.dndoop.game.utils.events.PlayerEventNotifier;

public class Monster extends Enemy {

    private int range;

    public Monster(String name, Health health, UnitStats stats,
                   Character character, Position position, int experience, int range) {
        super(name, health, stats, character, position, experience);

        //Add legality check on this: TODO
        this.range = range;

        PlayerEventNotifier.getInstance().addListener(this);
    }

    @Override
    public void onTick(PlayerEvent event) {
        if(position.range(event.getPosition()) <= range) {
            playerInRange(event.getPlayer());
        } else {
            randomMove();
        }
    }

    /**
     * Used only when player is in range onTick, handles the movement.
     * @param player the player.
     */
    public void playerInRange(Player player){
        Position playerPos = player.getPosition();
        int dx = position.getX() - playerPos.getX();
        int dy = position.getY() - playerPos.getY();

        //This is disgusting, but it is what it is.
        if(Math.abs(dx) > Math.abs(dy)) {
            if(dx > 0) {
                position.moveLeft();
            } else {
                position.moveRight();
            }
        } else {
            if(dy > 0) {
                position.moveUp();
            } else {
                position.moveDown();
            }
        }
    }

    /**
     * Used when player isn't in range.
     */
    public void randomMove() {
        int move = GameRandomizer.getInstance().getRandomInt(1, 4);
        switch (move) {
            case 1 -> position.moveDown();
            case 2 -> position.moveUp();
            case 3 -> position.moveLeft();
            case 4 -> position.moveRight();
        }
    }

    @Override
    public void onDeath() {
        PlayerEventNotifier.getInstance().removeListener(this);
    }
}
