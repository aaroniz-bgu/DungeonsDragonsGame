package org.dndoop.game.tile;

public class Health {

    private int healthPool;
    private int healthAmount;

    public Health(int initPool) {
        this.healthAmount = initPool;
        this.healthPool = initPool;
    }

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

    public int getHealthPool() {
        return healthPool;
    }

    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }
}
