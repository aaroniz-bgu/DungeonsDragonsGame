package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;

import java.beans.Visibility;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int tickCount;
    private boolean visible;

    public Trap(String name, Health health, UnitStats stats, Character character,
                Position position, int experience, int visibilityTime, int invisibilityTime,
                GameEventNotifier gameEventNotifier) {
        super(name, health, stats, character, position, experience, gameEventNotifier);

        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.tickCount = 0;
        this.visible = true;
    }

    /**
     * Used to control the tickCount field and control visibility of the trap
     */
    public void tickVisibility() {
        tickCount += 1;

        if(visible) {
            if(tickCount == visibilityTime) {
                tickCount = 0;
                visible = false;
            }
        } else {
            if(tickCount == invisibilityTime) {
                tickCount = 0;
                visible = true;
            }
        }
    }

    @Override
    public void visit(Empty empty) {
        //Do nothing
    }

    @Override
    public void visit(Player player) {
        //TODO Combat...
    }

    @Override
    public void buildMapEvents() {
        //TODO
    }

    @Override
    public void onTick() {
        tickVisibility();
    }

    @Override
    public void onDeath() {
        notifier.notify(new GameEvent(GameEventName.ENEMY_DEATH_EVENT, position, this));
    }

    public int getVisiblityTime() {
        return visibilityTime;
    }

    public int getInvisibilityTime() {
        return invisibilityTime;
    }

    public void setVisibilityTime(int visibilityTime) {
        this.visibilityTime = visibilityTime;
    }

    public void setInvisibilityTime(int invisibilityTime) {
        this.invisibilityTime = invisibilityTime;
    }

    public boolean isVisible() {
        return visible;
    }
    @Override
    public String toString() {
        return visible ? character.toString() : ".";
    }
}
