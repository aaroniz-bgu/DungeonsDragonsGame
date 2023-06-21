package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.players.HeroicUnit;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;

    public class Boss extends Monster implements HeroicUnit {

    private final int ABILITY_FREQUENCY;
    private int combatTicks;

    public Boss(Character character, String name, int health, int attack, int defense,
                int experience, int visionRange, int abilityFrequency,
                GameEventNotifier gameEventNotifier) {
        super(character, name, health, attack, defense, experience, visionRange, gameEventNotifier);

        this.ABILITY_FREQUENCY = abilityFrequency;
        this.combatTicks = 0;
    }

    /**
     * Reactions for events
     */
    @Override
    public void buildMapEvents() {
        events.put(GameEventName.PLAYER_ACTION_EVENT, (GameEvent event) -> {
            onTick();
            if(position.range(event.getPosition()) <= RANGE) {
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

    @Override
    public void castAbility(Unit unit) {
        unit.defend(getStats().getAttackPoints(), this);
    }

    /**
     * Game tick
     */
    @Override
    public void onTick() {

    }

    @Override
    public void castAbility() {
        //Do nothing...
    }
}
