package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEventNotifier;

public abstract class Player extends Unit {
    protected int xp;
    protected int level;

    /**
     * Creates a new player, starts at level 1 with 0 xp with the name, stats and position given.
     * @param name
     * @param health
     * @param stats
     * @param character
     * @param position
     */
    public Player(
            String name, Health health, UnitStats stats, Character character, Position position, GameEventNotifier gameEventNotifier
    ) {
        super(name, health, stats, character, position, gameEventNotifier);
        this.xp = 0;
        this.level = 1;
    }

    /**
     * Visitor design pattern, visits the pattern later.
     * @param unit self.
     */
    public void accept(Unit unit){
        unit.visit(this);
    }

    /**
     * The only 2 impl of visitor in the abstract class of player
     */
    @Override
    public void visit(Empty empty) {
        position.swapPositions(empty.getPosition());
    }
    /**
     * The only 2 impl of visitor in the abstract class of player
     */
    @Override
    public void visit(Player player) {
        //If this function gets invoked you f*****-up really hard buddy!
    }

    public abstract void onAbilityCast();
    public abstract void onLevelUp();
    public abstract void onDeath();
    /**
     * Levels up the player increasing and resetting its stats accordingly.
     */
    public void levelUp() {
        while(xp>=50*level) {
            xp = xp-50*level;
            level++;
            this.health.levelUp(level);
            this.stats.levelUp(level);
        }
    }
}
