package org.dndoop.game.utils.events;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible on notifying all the units that are alive and other objects about
 * various unit events, such as 'PlayerMovementEvent'
 * Each function returns it's instance for concatenating calls.
 */
public class GameEventNotifier implements Notifier {

    private final List<GameEventListener> LISTENERS;

    public GameEventNotifier() {
        this.LISTENERS = new ArrayList<>();
    }

    /**
     * Adds a listener to get updated on player events.
     * This method should be called only once within the listener.
     * @param listener - the listener that subscribes
     */
    public GameEventNotifier addListener(GameEventListener listener) {
        LISTENERS.add(listener);
        return this;
    }

    /**
     * Removes an object from the subscribed LISTENERS.
     * Used upon death of a monster.
     * @param listener - The listener that listens to the player events.
     */
    public GameEventNotifier removeListener(GameEventListener listener) {
        LISTENERS.remove(listener);
        return this;
    }

    /**
     * Fires up an event and notifies all objects that subscribed and observe player events.
     * @param event event to be sent
     */
    public GameEventNotifier notify(GameEvent event) {
        for(GameEventListener listener : LISTENERS) {
            listener.onGameEvent(event);
        }
        return this;
    }
}
