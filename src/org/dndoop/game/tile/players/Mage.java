package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Health;
import org.dndoop.game.tile.Player;
import org.dndoop.game.tile.Position;
import org.dndoop.game.tile.UnitStats;

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

    public Mage(String name, Health health, UnitStats stats, Character character, Position position,
                int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, health, stats, character, position);
        this.manaPool = manaPool;
        this.currentMana = manaPool/MANA_DIVISOR;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    /**
     * On mage ability cast event, removes ability mana cost from mana pool and then hits hitsCount enemies randomly within
     * abilityRange for damage equal to spellPower (each enemy may try to defend itself).
     * Will also stop if there are no living enemies left within abilityRange.
     */
    @Override
    public void onAbilityCast() {
        currentMana = currentMana - manaCost;
        int hits = 0;
        /*while (hits<hitsCount&&TODO exists living enemy such that range(enemy, player)<abilityRange) {
            //TODO deal damage to random enemy within abilityRange equal to spellPower
            //     (the enemy may try to defend itself)
            hits++;
        }*/
    }

    /**
     * On level up event, the mage gets the generic level up bonuses in addition to increasing their
     * mana pool by {@value #MANA_POOL_MULTIPLIER}*level and spell power by
     * {@value #SPELL_POWER_MULTIPLIER}*level and increasing its currentMana by
     * min(currentMana+manaPool/{@value #MANA_DIVISOR}, manaPool)
     */
    @Override
    public void onLevelUp() {
        levelUp();
        manaPool += MANA_POOL_MULTIPLIER*level;
        currentMana = Math.min(currentMana+manaPool/MANA_DIVISOR, manaPool);
        spellPower += SPELL_POWER_MULTIPLIER*level;
    }

    @Override
    public void onDeath() {
        //TODO
    }

    /**
     * On game tick event regenerates mana by min(manaPool, currentMana+{@value #MANA_TICK_MULTIPLIER}*level)
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
}
