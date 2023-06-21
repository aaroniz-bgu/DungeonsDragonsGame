package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;

public abstract class Enemy extends Unit {

    public Enemy(
            String name, int health, int attack, int defense,
            Character character, Position position, int experience,
            GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, character, experience, position, gameEventNotifier);
    }

    /**
     * Visitor design pattern, visits the pattern later.
     * @param unit self.
     */
    public void accept(Unit unit) {
        unit.visit(this);
    }

    /**
     * Enemies that meet should not do anything...
     */
    @Override
    public void visit(Enemy enemy) {
        //Do nothing...
    }

    @Override
    public void onDeath(Unit attacker) {
        m.send(name+" died. "+attacker.getName()+" gained "+xp+" experience");
        notifier.notify(new GameEvent(GameEventName.ENEMY_DEATH_EVENT, position, this));
    }

    public Character getTile() {
        return this.character;
    }

    @Override
    public String getDescription(){
        String description = super.getDescription();
        description += fixedLengthString("Experience Value: "+xp);
        return description;
    }
}
