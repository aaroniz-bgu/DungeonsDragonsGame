package org.dndoop.game.tile.playerClasses;

import org.dndoop.game.tile.Health;
import org.dndoop.game.tile.Player;
import org.dndoop.game.tile.Position;
import org.dndoop.game.tile.UnitStats;

public class Rogue extends Player {

    private int abilityCost;
    private int currentEnergy;
    private static final int ATTACK_POINTS_MULTIPLIER = 3;
    private static final int ENERGY_TICK_REGEN = 3;
    private static final int ENERGY_CAP = 100;
    private static final int ABILITY_RANGE = 2;
    public Rogue(String name, Health health, UnitStats stats, Character character, Position position,
                 int abilityCost) {
        super(name, health, stats, character, position);
        this.abilityCost = abilityCost;
        this.currentEnergy = ENERGY_CAP;
    }

    /**
     * On ability cast event, if the rogue has enough energy, remove ability cost from rogues current energy
     * and then for each enemy within range < {@value #ABILITY_RANGE}, deal damage equal to the rogues
     * attackPoints (each enemy attempts to defend itself).
     */
    @Override
    public void onAbilityCast() {
        currentEnergy -= abilityCost;
        //TODO for each enemy within range < ABILITY_RANGE,
        // deal damage equal to the rogues attackPoints (each enemy attempts to defend itself).
    }

    /**
     * On level up event, the rogue gets the generic level up bonuses in addition to resetting its
     * energy to cap and gaining attack points equal to {@value #ATTACK_POINTS_MULTIPLIER}*level.
     */
    @Override
    public void onLevelUp() {
        levelUp();
        currentEnergy = ENERGY_CAP;
        stats.addAttackPoints(ATTACK_POINTS_MULTIPLIER*level);
    }

    /**
     * On game tick event, the rogue regens {@value #ENERGY_TICK_REGEN} but caps at {@value #ENERGY_CAP}.\
     */
    @Override
    public void onTick() {
        currentEnergy = Math.min(currentEnergy+ENERGY_TICK_REGEN, ENERGY_CAP);
    }

    public int getAbilityCost() {
        return abilityCost;
    }

    public void setAbilityCost(int abilityCost) {
        this.abilityCost = abilityCost;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }
}
