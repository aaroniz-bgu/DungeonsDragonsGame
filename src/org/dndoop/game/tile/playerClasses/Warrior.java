package org.dndoop.game.tile.playerClasses;

import org.dndoop.game.tile.Health;
import org.dndoop.game.tile.Position;
import org.dndoop.game.tile.UnitStats;
import org.dndoop.game.tile.Player;

public class Warrior extends Player{
    private int abilityCD;
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
    public Warrior(String name, Health health, UnitStats stats, Character character, Position position) {
        super(name, health, stats, character, position);
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
        health.setHealthAmount(Math.min(health.getHealthAmount() + ABILITY_DEFENSE_MULTIPLIER*stats.getDefensePoints(),
                health.getHealthPool()));
        //TODO Randomly hit one enemy within range<ABILITY_RANGE for an amount equal to
        // the warriors health pool/ABILITY_HEALTH_DIVISOR.
    }

    /**
     * On level up event, the warrior gets the generic level up bonuses in addition to resetting cd,
     * increasing its health pool by {@value #HEALTH_POOL_MULTIPLIER}*level,
     * attack points by {@value #ATTACK_POINTS_MULTIPLIER}*level and
     * defense points by {@value #DEFENSE_POINTS_MULTIPLIER}*level.
     */
    @Override
    public void onLevelUp() {
        levelUp();
        cdRemaining = 0;
        stats.addAttackPoints(ATTACK_POINTS_MULTIPLIER*level);
        stats.addDefensePoints(DEFENSE_POINTS_MULTIPLIER*level);
        health.increasePool(HEALTH_POOL_MULTIPLIER*level);
        health.setHealthAmount(health.getHealthPool());
    }

    /**
     * On game tick event lowers the warriors ability cooldown by 1.
     */
    @Override
    public void onTick() {
        if(cdRemaining>0)
            cdRemaining--;
    }
}
