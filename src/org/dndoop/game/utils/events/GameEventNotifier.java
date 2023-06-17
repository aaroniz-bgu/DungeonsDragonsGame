package org.dndoop.game.utils.events;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible on notifying all the units that are alive and other objects about
 * various unit events, such as 'PlayerMovementEvent'
 * Each function returns it's instance for concatenating calls.
 */
public class GameEventNotifier implements Notifier {

    private boolean notifying;
    private final List<GameEventListener> LISTENERS;
    private List<GameEventListener> toRemove;
    private List<GameEventListener> toAdd;

    public GameEventNotifier() {
        this.notifying = false;
        this.LISTENERS = new ArrayList<>();
        this.toRemove = new ArrayList<>();
        this.toAdd = new ArrayList<>();
    }

    /**
     * Adds a listener to get updated on player events.
     * This method should be called only once within the listener.
     * @param listener - the listener that subscribes
     */
    public GameEventNotifier addListener(GameEventListener listener) {
        if(notifying) {
            toAdd.add(listener);
        } else {
            LISTENERS.add(listener);
        }
        return this;
    }

    /**
     * Removes an object from the subscribed LISTENERS.
     * Used upon death of a monster.
     * @param listener - The listener that listens to the player events.
     */
    public GameEventNotifier removeListener(GameEventListener listener) {
        if(notifying) {
            toRemove.add(listener);
        } else {
            LISTENERS.remove(listener);
        }
        return this;
    }

    /**
     * Fires up an event and notifies all objects that subscribed and observe player events.
     * @param event event to be sent
     */
    public GameEventNotifier notify(GameEvent event) {
        notifying = true;
        for(GameEventListener listener : LISTENERS) {
            listener.onGameEvent(event);
        }
        afterNotification();
        return this;
    }

    private void afterNotification() {
        notifying = false;
        if(!toRemove.isEmpty()) {
            LISTENERS.removeAll(toRemove);
            toRemove = new ArrayList<>();
        }
        if(!toAdd.isEmpty()) {
            LISTENERS.addAll(toAdd);
            toAdd = new ArrayList<>();
        }
    }
}
