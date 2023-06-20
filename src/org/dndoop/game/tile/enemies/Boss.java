package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.players.HeroicUnit;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.GameRandomizer;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;

public class Boss extends Enemy implements HeroicUnit {

    private final int VISION_RANGE;
    private final int ABILITY_FREQUENCY;
    private int combatTicks;

    public Boss(Character character, String name, int health, int attack, int defense,
                int experience, int visionRange, int abilityFrequency,
                GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, character, null, experience, gameEventNotifier);

        this.VISION_RANGE = visionRange;
        this.ABILITY_FREQUENCY = abilityFrequency;
        this.combatTicks = 0;
    }

    @Override
    public void visit(Empty empty) {
        getPosition().swapPositions(empty.getPosition());
    }

    @Override
    public void visit(Player player) {
        attack(player);
    }

    /**
     * Reactions for events
     */
    @Override
    public void buildMapEvents() {
        events.put(GameEventName.PLAYER_ACTION_EVENT, (GameEvent event) -> {
            onTick();
            if(position.range(event.getPosition()) <= VISION_RANGE) {
                if(combatTicks == ABILITY_FREQUENCY) {
                    combatTicks = 0;
                    castAbility();
                } else {
                    combatTicks += 1;
                    playerInRange(event.getActor());
                }
            } else {
                combatTicks = 0;
                randomMove();
            }
        });
        events.put(GameEventName.PLAYER_ABILITY_CAST_EVENT, (GameEvent event) -> {
            event.interactWithEvent(this);
        });
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
        Direction[] DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.STAY};
        int direction = GameRandomizer.getInstance().getRandomInt(0, 4);
        move(DIRECTIONS[direction]);
    }

    /**
     * Game tick
     */
    @Override
    public void onTick() {

    }

    @Override
    public void castAbility(Unit unit) {
        unit.defend(getStats().getAttackPoints(), this);
    }

    @Override
    public void castAbility() {
        //Do nothing...
    }
}