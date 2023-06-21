package org.dndoop.game.tile.tile_utils;

/**
 * Helper class to handle attack & defense points of a unit.
 */
public class UnitStats {
    public static final int ATTACK_MULTIPLIER = 4;
    public static final int DEFENSE_MULTIPLIER = 1;

    private int attackPoints;
    private int defensePoints;

    public UnitStats(int attackPoints, int defensePoints) {
        if(attackPoints >= 0 && defensePoints >= 0){
            this.attackPoints = attackPoints;
            this.defensePoints = defensePoints;
        }
        else {
            //TODO
            //exception IllegalStatsAccepted
        }
    }

    /**
     * Used only for players!, a helper function to level up a player!
     * @param level - the level the player reached to
     */
    public void levelUp(int level) {
        increaseAttackPoints(level * ATTACK_MULTIPLIER);
        increaseDefensePoints(level * DEFENSE_MULTIPLIER);
    }

    /**
     * Increases the attack points of the unit
     * @param amount integer >= 0, which describes by how many points will be increased
     */
    public void increaseAttackPoints(int amount){
        if(amount >= 0) this.attackPoints += amount;
    }

    /**
     * Increases the defence points of a unit
     * @param amount integer >= 0, which describes by how many points will be increased
     */
    public void increaseDefensePoints(int amount) {
        if(amount >= 0) this.defensePoints += amount;
    }

    /**
     * @return get attack points
     */
    public int getAttackPoints() {
        return attackPoints;
    }

    /**
     * @param attackPoints the attack points to set, non-negative
     */
    public void setAttackPoints(int attackPoints) {
        if(attackPoints >= 0) this.attackPoints = attackPoints;
    }

    /**
     * @return the defense points of a unit
     */
    public int getDefensePoints() {
        return defensePoints;
    }

    /**
     *
     * @param defensePoints the defense points to set, non-negative
     */
    public void setDefensePoints(int defensePoints) {
        if(defensePoints >= 0) this.defensePoints = defensePoints;
    }
}
