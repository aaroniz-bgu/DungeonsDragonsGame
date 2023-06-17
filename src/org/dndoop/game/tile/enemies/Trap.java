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

    public Trap(Character character,String name, int health, int attack, int defense,
                int experience, int visibilityTime, int invisibilityTime, GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, character, null, experience, gameEventNotifier);

        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.tickCount = 0;
        this.visible = true;
    }
    public Trap(Character character,String name, int health, int attack, int defense,
                Position position, int experience, int visibilityTime, int invisibilityTime,
                GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, character, position, experience, gameEventNotifier);

        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.tickCount = 0;
        this.visible = true;
    }

    /**
     * Used to control the tickCount field and control visibility of the trap
     */
    public void tickVisibility() {
        visible = tickCount < visibilityTime;

        if(tickCount >= (visibilityTime + invisibilityTime)) {
            tickCount = 0;
        } else {
            tickCount += 1;
        }
    }

    @Override
    public void visit(Empty empty) {
        //Do nothing
    }

    @Override
    public void visit(Player player) {
        //Can't actually visit a player, damaging handled below in event route.
    }

    @Override
    public void buildMapEvents() {
        events.put(GameEventName.PLAYER_ACTION_EVENT, (GameEvent event) -> {
            onTick();
            if (position.range(event.getPosition()) < 2) {
                attack(event.getActor());
            }
        });
        events.put(GameEventName.PLAYER_ABILITY_CAST_EVENT, (GameEvent event) -> {
            event.interactWithEvent(this);
        });
    }

    @Override
    public void onTick() {
        tickVisibility();
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
