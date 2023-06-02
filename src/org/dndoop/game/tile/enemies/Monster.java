package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.*;
import org.dndoop.game.utils.GameRandomizer;

public class Monster extends Enemy {

    private int range;

    public Monster(String name, Health health, UnitStats stats,
                   Character character, Position position, int experience, int range) {
        super(name, health, stats, character, position, experience);

        //Add legality check on this: TODO
        this.range = range;
    }

    @Override
    public void onTick() {
        //THIS METHOD SYSTEM IS GOING TO CHANGE!
        /* But for starters good to know:
         * if player in range
         * else do random move
         */
    }

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

    }
}
