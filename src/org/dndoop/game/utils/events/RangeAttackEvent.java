package org.dndoop.game.utils.events;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;

import java.util.ArrayList;

import static org.dndoop.game.utils.events.GameEventName.PLAYER_ABILITY_CAST_EVENT;

/**
 * An event to collect potential targets within a certain range, can be fed with a pointer to a list or return a new list.
 * Results would be identical either-ways.
 */
public class RangeAttackEvent extends GameEvent {

    private final ArrayList<Unit> TARGETS;
    private final int RANGE;


    /**
     * Creates a new RangeAttackEvent, does not fire it. Event populates a collection of possible targets withing given range.
     * It's better to save pointer to it for later accessing the target list.
     * @param position The position of the player at the time of event creation - would be the center of the range.
     * @param player The player who is the actor of this event.
     * @param range The range to look for targets within.
     */
    public RangeAttackEvent(Position position, Player player, int range) {
        this(position,player,range, new ArrayList<>());
    }

    /**
     * Creates a new RangeAttackEvent, does not fire it. Event populates a given collection of possible targets withing given range.
     * @param position The position of the player at the time of event creation - would be the center of the range.
     * @param player The player who is the actor of this event.
     * @param range The range to look for targets within.
     * @param pointer An instance of your list that would be populated.
     */
    public RangeAttackEvent(Position position, Player player, int range, ArrayList<Unit> pointer) {
        super(PLAYER_ABILITY_CAST_EVENT, position, player);
        TARGETS = pointer;
        RANGE = range;
    }

    /**
     * Adds units that may be potential for ability cast.
     * @param unit the unit that interacts with the event.
     */
    @Override
    public void interactWithEvent(Unit unit) {
        if(getPosition().range(unit.getPosition()) < RANGE) {
            TARGETS.add(unit);
        }
    }

    /**
     * @return All the potential targets within the range given centered around position.
     */
    public ArrayList<Unit> getTargets() {
        return TARGETS;
    }
}
