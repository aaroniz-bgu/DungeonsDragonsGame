package org.dndoop.game.tile.players;

import org.dndoop.game.tile.Unit;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.events.RangeAttackEvent;

import java.util.ArrayList;

public class Hunter extends Player {

    private final static int ARROW_MULTIPLIER = 10;
    private final static int ATTACK_MULTIPLIER = 2;
    private final static int DEFENSE_MULTIPLIER = 1;

    private int arrowCount;
    private int tickCount;
    private final int RANGE;

    public Hunter(String name, int health, int attack, int defense, int range, GameEventNotifier gameEventNotifier) {
        super(name, health, attack, defense, null, gameEventNotifier,
                0, ATTACK_MULTIPLIER, DEFENSE_MULTIPLIER);
        this.RANGE = range;
        this.tickCount = 0;
        this.arrowCount = level * ARROW_MULTIPLIER;
    }
    @Override
    public void castAbility() {
        if(arrowCount > 0) {
            onAbilityCast();
        }
    }

    @Override
    public void onAbilityCast() {
        arrowCount -= 1;

        ArrayList<Unit> potentialTargets = new ArrayList<>();
        notifier.notify(new RangeAttackEvent(position, this, RANGE, potentialTargets));

        Unit minimalTarget = null;
        double minDistance = RANGE;

        for(Unit target : potentialTargets) {
            double distanceToTarget = position.range(target.getPosition());
            if(distanceToTarget < minDistance) {
                minDistance = distanceToTarget;
                minimalTarget = target;
            }
        }
        if(minimalTarget != null) {
            minimalTarget.defend(getStats().getAttackPoints(), this);
        }
    }

    @Override
    public void onLevelUp() {
        arrowCount += level * ARROW_MULTIPLIER;
        getStats().increaseAttackPoints(ATTACK_MULTIPLIER * level);
        getStats().increaseDefensePoints(DEFENSE_MULTIPLIER * level);
    }

    @Override
    public void onTick() {
        if(tickCount == 10) {
            arrowCount += level;
            tickCount = 0;
        } else {
            tickCount += 1;
        }
        super.onTick();
    }

    @Override
    public String getDescription() {
        String output = super.getDescription();
        output += fixedLengthString("Arrows: "+arrowCount);
        return output;
    }
}
