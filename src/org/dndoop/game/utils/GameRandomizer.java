package org.dndoop.game.utils;

import java.util.Random;

/**
 * The games centralized randomizer, the only randomizer used across the game.
 * With the goal to replicate events for debugging when used with a seed.
 */
public class GameRandomizer {

    private static GameRandomizer gameRandomizer = null;
    private final Random random;

    private GameRandomizer(Random random) {
        this.random = random;
    }

    /**
     * A method to get the static instance of the game randomizer.
     * @return - an instance of the game randomizer
     */
    public static GameRandomizer getInstance() {
        if(gameRandomizer == null) {
            long seed = (long) Math.ceil(Math.random() * Math.pow(10, 8));
            System.out.println("SEED:"+seed);
            gameRandomizer = new GameRandomizer(new Random(seed));
        }

        return gameRandomizer;
    }

    /**
     * Used to generate with a seed to replicate behavior across randomized events.
     * Used only once.
     * @param seed - Seed for the randomizer
     * @return an instance of the game randomizer
     */
    public static GameRandomizer getInstance(long seed) {
        if(gameRandomizer != null) {
            //TODO - Throw exception
        }

        gameRandomizer = new GameRandomizer(new Random(seed));
        return gameRandomizer;
    }

    /**
     * @return random int
     */
    public int getRandomInt() {
        return random.nextInt();
    }

    /**
     * @param min - the least value that can be returned (inclusive)
     * @param max - the max value that can be returned (inclusive)
     * @return Returns a random int within the inclusive range of [min, max].
     */
    public int getRandomInt(int min, int max) {
        return random.nextInt(min, max + 1);
    }

    /**
     * @return random double
     */
    public double getRandomDouble() {
        return random.nextDouble();
    }

    /**
     * @param min - the least value that can be returned (inclusive)
     * @param max - the max value that can be returned (inclusive)
     * @return Returns a random double within the inclusive range of [min, max].
     */
    public double getRandomDouble(double min, double max) {
        return random.nextDouble(min, max + 1);
    }
}
