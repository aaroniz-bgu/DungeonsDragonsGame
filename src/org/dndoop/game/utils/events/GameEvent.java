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

    /**
     * Creating new game event, not firing it.
     * @param name Name of event
     * @param position Position of unit at the time of event
     * @param actor Unit that acts upon invoking the event
     */
    public GameEvent(GameEventName name, Position position, Unit actor) {
        this(name, position, actor, false);
    }

    /**
     * Creating new game event, not firing it.
     * @param name Name of event
     * @param position Position of unit at the time of event
     * @param actor Unit that acts upon invoking the event
     */
    public GameEvent(GameEventName name, Position position, Player actor) {
        this(name, position, actor, true);
    }

    /**
     * Creating new game event without a name only for testing purposes!.
     * @param position Position of unit at the time of event
     * @param actor Unit that acts upon invoking the event
     */
    public GameEvent(Position position, Unit actor) {
        this(null, position, actor);
    }

    /**
     * Creating new game event without a name only for testing purposes!.
     * @param position Position of unit at the time of event
     * @param actor Unit that acts upon invoking the event
     */
    public GameEvent(Position position, Player actor) {
        this(null, position, actor);
    }

    /**
     * Used for subclasses where a receiver might need to interact with the event.
     * @param unit the unit that interacts with the event.
     */
    public void interactWithEvent(Unit unit) {
        //Implemented only in subclasses;
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

    /**
     * @deprecated please avoid using it.
     * @return true iff a player invoked this event.
     */
    public boolean isPlayerEvent() {
        return IS_PLAYER;
    }
}
