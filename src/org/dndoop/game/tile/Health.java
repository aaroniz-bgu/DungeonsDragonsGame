package org.dndoop.game.tile;

public class Health {
    private int healthPool;
    private int healthAmount;
    private static final int HEALTH_POOL_MULTIPLIER = 10;

    public Health(int initPool) {
        this.healthAmount = initPool;
        this.healthPool = initPool;
    }

    /**
     * Regenerate the amount of health given as a parameter, capping at health pool.
     * @param amount
     */
    public void regen(int amount) {
        healthAmount = Math.min(healthPool, healthAmount + amount);
    }

    /**
     * A function to handle damage taking, returns upon death;
     * @param amount the amount of damage points taken from the enemy
     * @return true if unit died and false otherwise
     */
    public boolean damage(int amount) {
        healthAmount = Math.max(healthAmount - amount, 0);
        return healthAmount == 0;
    }

    /**
     * Gets health pool.
     * @return healthPool
     */
    public int getHealthPool() {
        return healthPool;
    }

    /**
     * Sets health pool to new
     * @param healthPool
     */
    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }

    /**
     * Gets health amount.
     * @return healthAmount
     */
    public int getHealthAmount() {
        return healthAmount;
    }

    /**
     * Sets healthAmount to new healthAmount
     * @param healthAmount
     */
    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    /**
     * Increases healthPool by specified amount.
     * @param amount
     */
    public void increasePool(int amount) {
        this.healthPool += amount;
    }

    /**
     * A method to help when a player levels up, increases their healthPool by
     * their new level*HEALTH_POOL_MULTIPLIER and then sets their health to that amount.
     * @param level
     */
    public void levelUp(int level) {
        increasePool(HEALTH_POOL_MULTIPLIER*level);
        setHealthAmount(healthPool);
    }
}
