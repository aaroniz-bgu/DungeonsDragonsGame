package org.dndoop.game.utils.events;

import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;

public class PlayerEvent {
    private final String NAME;
    private final Position POSITION;
    private final Player PLAYER;

    /**
     * The default constructor for an event, should be only generated in Player class/ related
     * actions.
     * @param name - Name of event e.g 'PlayerMovementEvent'
     * @param position The position of the player at time of event firing
     */
    public PlayerEvent(String name, Position position, Player player) {
        if(position == null) {
            //TODO THROW ILLEGAL POSITION EXCEPTION
        }

        this.NAME = name;
        this.POSITION = position;
        this.PLAYER = player;
    }

    public PlayerEvent(Position position, Player player) {
        this(null, position, player);
    }

    /**
     * @return Event's name
     */
    public String getName() {
        return NAME;
    }

    /**
     * @return Player's position at the time that the event was fired
     */
    public Position getPosition() {
        return POSITION;
    }

    /**
     * @return the player which fired up the event
     */
    public Player getPlayer() {
        return PLAYER;
    }
}
