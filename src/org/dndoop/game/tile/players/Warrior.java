package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.GameRandomizer;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.events.RangeAttackEvent;

import java.util.ArrayList;

public class Warrior extends Player{
    private final int abilityCD;
    private int cdRemaining;
    private static final int HEALTH_POOL_MULTIPLIER = 5;
    private static final int ATTACK_POINTS_MULTIPLIER = 2;
    private static final int DEFENSE_POINTS_MULTIPLIER = 1;
    private static final int ABILITY_DEFENSE_MULTIPLIER = 10;
    private static final int ABILITY_HEALTH_DIVISOR = 10;
    private static final int ABILITY_RANGE = 3;
    /**
     * Creates a new warrior with the given parameters.
     * @param name
     * @param health
     * @param stats
     * @param character
     * @param position
     */
    public Warrior(String name, Health health, UnitStats stats, Character character, Position position,
                   int abilityCoolDown, GameEventNotifier gameEventNotifier) {
        super(name, health, stats, character, position, gameEventNotifier);

        abilityCD = abilityCoolDown;
        cdRemaining = 0;
    }

    /**
     * Warrior can cast its ability only when it's cool-down reached 0.
     */
    @Override
    public void castAbility() {
        if(cdRemaining == 0) {
            castAbility();
        }
    }

    /**
     * On ability cast event, sets their ability cooldown to full,
     * adds to their health {@value #ABILITY_DEFENSE_MULTIPLIER}*defense stats unless capped,
     * and randomly hits one enemy within range (<{@value #ABILITY_RANGE}) for an amount equal to
     * the warriors health pool/{@value #ABILITY_HEALTH_DIVISOR}.
     */
    @Override
    public void onAbilityCast() {
        cdRemaining = abilityCD;
        //Regen 10
        getHealth().regen(ABILITY_DEFENSE_MULTIPLIER * stats.getDefensePoints());
        //Creating and getting potential targets.
        ArrayList<Unit> potentialTargets = new ArrayList<>();
        notifier.notify(new RangeAttackEvent(position, this, ABILITY_RANGE, potentialTargets));
        //Getting randomly chosen target:
        if(!potentialTargets.isEmpty()) {
            int randomTarget = GameRandomizer.getInstance().getRandomInt(0, potentialTargets.size() - 1);
            Unit target = potentialTargets.get(randomTarget);
            //Doing the ouchie:
            target.defend(getHealth().getHealthPool()/ABILITY_HEALTH_DIVISOR);
        }
        /* Before use of regen, leaving it here just to be safe.
        health.setHealthAmount(
                Math.min(
                        health.getHealthAmount() + ABILITY_DEFENSE_MULTIPLIER * stats.getDefensePoints(),
                        health.getHealthPool()
                )
        );
         */
    }

    /**
     * On level up event, the warrior gets the generic level up bonuses in addition to resetting cd,
     * increasing its health pool by {@value #HEALTH_POOL_MULTIPLIER}*level,
     * attack points by {@value #ATTACK_POINTS_MULTIPLIER}*level and
     * defense points by {@value #DEFENSE_POINTS_MULTIPLIER}*level.
     */
    @Override
    public void onLevelUp() {
        cdRemaining = 0;
        stats.increaseAttackPoints(ATTACK_POINTS_MULTIPLIER*level);
        stats.increaseDefensePoints(DEFENSE_POINTS_MULTIPLIER*level);
        health.increasePool(HEALTH_POOL_MULTIPLIER*level);
        health.setHealthAmount(health.getHealthPool());
    }

    @Override
    public void visit(Enemy enemy) {
        attack(enemy);
    }

    /**
     * On game tick event lowers the warriors ability cool-down by 1.
     */
    @Override
    public void onTick() {
        if(cdRemaining>0)
            cdRemaining--;
    }
}
