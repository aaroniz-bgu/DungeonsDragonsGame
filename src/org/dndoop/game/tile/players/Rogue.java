package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.events.RangeAttackEvent;

import java.util.ArrayList;

public class Rogue extends Player {

    private int abilityCost;
    private int currentEnergy;
    private static final int ATTACK_POINTS_MULTIPLIER = 3;
    private static final int ENERGY_TICK_REGEN = 3;
    private static final int ENERGY_CAP = 100;
    private static final int ABILITY_RANGE = 2;

    public Rogue(String name, int health, int attack, int defense, int abilityCost, GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, null, gameEventNotifier, 0, ATTACK_POINTS_MULTIPLIER, 0);
        this.abilityCost = abilityCost;
        this.currentEnergy = ENERGY_CAP;
    }
    public Rogue(String name, int health, int attack, int defense, Position position,
                 int abilityCost, GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, position, gameEventNotifier, 0, ATTACK_POINTS_MULTIPLIER, 0);
        this.abilityCost = abilityCost;
        this.currentEnergy = ENERGY_CAP;
    }

    /**
     * Called within the Joystick class only casts ability if the requirements are met.
     */
    @Override
    public void castAbility() {
        if(getCurrentEnergy() >= getAbilityCost()) {
            onAbilityCast();
        }
    }

    /**
     * On ability cast event, if the rogue has enough energy, remove ability cost from rogues current energy
     * and then for each enemy within range < {@value #ABILITY_RANGE}, deal damage equal to the rogues
     * attackPoints (each enemy attempts to defend itself).
     */
    @Override
    public void onAbilityCast() {
        currentEnergy -= abilityCost;

        ArrayList<Unit> targets = new ArrayList<>();
        notifier.notify(new RangeAttackEvent(position, this, ABILITY_RANGE, targets));

        for(Unit target : targets) {
            //Since we're not rolling an attack and just maxing our attack.
            target.defend(getStats().getAttackPoints(), this);
        }
    }

    /**
     * On level up event, the rogue gets the generic level up bonuses in addition to resetting its
     * energy to cap and gaining attack points equal to {@value #ATTACK_POINTS_MULTIPLIER}*level.
     */
    @Override
    public void onLevelUp() {
        currentEnergy = ENERGY_CAP;
        stats.increaseAttackPoints(ATTACK_POINTS_MULTIPLIER*level);
    }

    /**
     * On game tick event, the rogue regens {@value #ENERGY_TICK_REGEN} but caps at {@value #ENERGY_CAP}.\
     */
    @Override
    public void onTick() {
        currentEnergy = Math.min(currentEnergy+ENERGY_TICK_REGEN, ENERGY_CAP);
        super.onTick();
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

    @Override
    public String getDescription(){
        String description = super.getDescription();
        description += fixedLengthString("Energy: "+currentEnergy+"/"+ENERGY_CAP);
        return description;
    }
}
