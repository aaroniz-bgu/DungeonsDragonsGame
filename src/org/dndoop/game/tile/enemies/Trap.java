package org.dndoop.game.tile.enemies;

import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventNotifier;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int tickCount;
    private boolean visible;

    public Trap(String name, Health health, UnitStats stats, Character character,
                Position position, int experience, int visibilityTime, int invisibilityTime) {
        super(name, health, stats, character, position, experience);

        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.tickCount = 0;
        this.visible = true;

        GameEventNotifier.getInstance().addListener(this);
    }

    /**
     * Used to control the tickCount field and control visibility of the trap
     * TODO add visibility control once MVC is implemented
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
    public void onTick(GameEvent event) {
        tickVisibility();
        //rest of that
    }

    @Override
    public void onDeath() {
        GameEventNotifier.getInstance().removeListener(this);
    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    public String toString() {
        return visible ? character.toString() : ".";
    }
}
