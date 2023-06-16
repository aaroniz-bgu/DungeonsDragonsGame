package org.dndoop.game.utils.events;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;

public class GameEvent {
    private final GameEventName NAME;
    private final Position POSITION;
    private final Unit ACTOR;
    private final boolean IS_PLAYER;

    /**
     * The default constructor for an event, should be only generated in Player class/ related
     * actions.
     * @param name - Name of event e.g 'PlayerMovementEvent'
     * @param position The position of the player at time of event firing
     */
    private GameEvent(GameEventName name, Position position, Unit actor, boolean isPlayer) {
        if(position == null) {
            //TODO THROW ILLEGAL POSITION EXCEPTION
        }

        this.NAME = name;
        this.POSITION = new Position(position);
        this.ACTOR = actor;
        this.IS_PLAYER = isPlayer;
    }

    public GameEvent(GameEventName name, Position position, Unit actor) {
        this(name, position, actor, false);
    }

    public GameEvent(GameEventName name, Position position, Player actor) {
        this(name, position, actor, true);
    }

    public GameEvent(Position position, Unit actor) {
        this(null, position, actor);
    }

    public GameEvent(Position position, Player actor) {
        this(null, position, actor);
    }

    /**
     * @return Event's name
     */
    public GameEventName getName() {
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
    public Unit getActor() {
        return ACTOR;
    }

    public boolean isPlayerEvent() {
        return IS_PLAYER;
    }
}
