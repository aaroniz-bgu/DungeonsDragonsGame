package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Empty;
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

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    private static final int MANA_POOL_MULTIPLIER = 25;
    private static final int SPELL_POWER_MULTIPLIER = 10;
    private static final int MANA_DIVISOR = 4;
    private static final int MANA_TICK_MULTIPLIER = 1;

    public Mage(String name, int health, int attack, int defense,
                int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange, GameEventNotifier gameEventNotifier) {
        this(name, health, attack, defense, null, manaPool, manaCost, spellPower, hitsCount, abilityRange, gameEventNotifier);
    }

    public Mage(String name, int health, int attack, int defense, Position position,
                int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange,
                GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, position, gameEventNotifier);
        this.manaPool = manaPool;
        this.currentMana = manaPool/MANA_DIVISOR;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    /**
     * Used within Joystick, only casts ability if mage's current mana >= cost
     */
    @Override
    public void castAbility() {
        if(getCurrentMana() >= getManaCost()) {
            onAbilityCast();
        }
    }

    /**
     * On mage ability cast event, removes ability mana cost from mana pool and then hits hitsCount enemies randomly within
     * abilityRange for damage equal to spellPower (each enemy may try to defend itself).
     * Will also stop if there are no living enemies left within abilityRange.
     */
    @Override
    public void onAbilityCast() {
        currentMana = currentMana - manaCost;

        ArrayList<Unit> potentialTargets = new ArrayList<>();
        notifier.notify(new RangeAttackEvent(position, this, abilityRange, potentialTargets));

        int hits = 0;
        while(hits < hitsCount && !potentialTargets.isEmpty()) {
            //Getting the random target.
            int randomTarget = GameRandomizer.getInstance().getRandomInt(0, potentialTargets.size() - 1);
            Unit target = potentialTargets.get(randomTarget);

            //Since here we're attacking with custom amount of damage.
            target.defend(spellPower);

            //If target died remove it from potential targets.
            if(!target.isAlive()) {
                potentialTargets.remove(target);
            }
            hits += 1;
        }
    }

    /**
     * On level up event, the mage gets the generic level up bonuses in addition to increasing their
     * mana pool by {@value #MANA_POOL_MULTIPLIER}*level and spell power by
     * {@value #SPELL_POWER_MULTIPLIER}*level and increasing its currentMana by
     * min(currentMana+manaPool/{@value #MANA_DIVISOR}, manaPool)
     */
    @Override
    public void onLevelUp() {
        manaPool += MANA_POOL_MULTIPLIER*level;
        currentMana = Math.min(currentMana+manaPool/MANA_DIVISOR, manaPool);
        spellPower += SPELL_POWER_MULTIPLIER*level;
    }

    @Override
    public void visit(Enemy enemy) {
        attack(enemy);
    }

    /**
     * On game tick regenerates mana by min(manaPool, currentMana+{@value #MANA_TICK_MULTIPLIER}*level)
     */
    @Override
    public void onTick() {
        currentMana = Math.min(manaPool, currentMana+MANA_TICK_MULTIPLIER*level);
    }

    public int getManaPool() {
        return manaPool;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public int getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(int hitsCount) {
        this.hitsCount = hitsCount;
    }

    public int getAbilityRange() {
        return abilityRange;
    }

    public void setAbilityRange(int abilityRange) {
        this.abilityRange = abilityRange;
    }

    @Override
    public String getDescription(){
        String description = super.getDescription();
        description += fixedLengthString("Mana: "+currentMana+"/"+manaPool);
        description += fixedLengthString("Spell Power: "+spellPower);
        return description;
    }
}
