package org.dndoop.game.board;

import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to reroute user input and invoke player actions accordingly.
 */
public class Joystick {

    private static final Map<String, Direction> DIRECTIONS = new HashMap<>();
    private final Player player;
    public Joystick(Player player) {
        this.player = player;

        if(DIRECTIONS.isEmpty()) {
            initDirections();
        }
    }

    /**
     * Just populates the map of possible directions in-case it's empty.
     */
    private void initDirections() {
        DIRECTIONS.put("w", Direction.UP);
        DIRECTIONS.put("s", Direction.DOWN);
        DIRECTIONS.put("a", Direction.LEFT);
        DIRECTIONS.put("d", Direction.RIGHT);
        DIRECTIONS.put("q", Direction.STAY);
    }

    /**
     * Routes the user input to the right place.
     * @param s User input.
     */
    public void input(String s) {
        if(s.length() == 1) {
            if(s.equals("e")) {
                specialAbilityCast();
            } else if (DIRECTIONS.containsKey(s)) {
                move(DIRECTIONS.get(s));
            }
        }
    }

    /**
     * Moves the player/ just invokes an action.
     * @param direction Direction to move in.
     */
    public void move(Direction direction) {
        player.move(direction);
    }

    /**
     * Casts players special ability.
     */
    public void specialAbilityCast() {
        player.castAbility();
    }
}
