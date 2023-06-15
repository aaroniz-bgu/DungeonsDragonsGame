package org.dndoop.game.utils.events;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton responsible on notifying all the units that are alive and other objects about
 * various player events, such as 'PlayerMovementEvent'
 */
public class GameEventNotifier {

    private static GameEventNotifier instance = null;

    private List<GameEventListener> listeners;

    private GameEventNotifier() {
        this.listeners = new ArrayList<>();
    }

    public static GameEventNotifier getInstance() {
        if(instance == null) {
            instance = new GameEventNotifier();
        }

        return instance;
    }

    /**
     * Adds a listener to get updated on player events.
     * This method should be called only once within the listener.
     * @param listener - the listener that subscribes
     */
    public GameEventNotifier addListener(GameEventListener listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * Removes an object from the subscribed listeners.
     * Used upon death of a monster.
     * @param listener - The listener that listens to the player events.
     * @return true if was in the listeners, false otherwise.
     */
    public GameEventNotifier removeListener(GameEventListener listener) {
        listeners.remove(listener);
        return this;
    }

    /**
     * Fires up an event and notifies all objects that subscribed and observe player events.
     * @param event event to be sent
     */
    public GameEventNotifier notify(GameEvent event) {
        for(GameEventListener listener : listeners) {
            listener.onTick(event);
        }
        return this;
    }
}
