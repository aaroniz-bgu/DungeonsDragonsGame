package org.dndoop.game.utils.events;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton responsible on notifying all the units that are alive and other objects about
 * various player events, such as 'PlayerMovementEvent'
 */
public class PlayerEventNotifier {

    private static PlayerEventNotifier instance = null;

    private List<PlayerEventListener> listeners;

    private PlayerEventNotifier() {
        this.listeners = new ArrayList<>();
    }

    public static PlayerEventNotifier getInstance() {
        if(instance == null) {
            instance = new PlayerEventNotifier();
        }

        return instance;
    }

    /**
     * Adds a listener to get updated on player events.
     * This method should be called only once within the listener.
     * @param listener - the listener that subscribes
     */
    public void addListener(PlayerEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an object from the subscribed listeners.
     * Used upon death of a monster.
     * @param listener - The listener that listens to the player events.
     * @return true if was in the listeners, false otherwise.
     */
    public boolean removeListener(PlayerEventListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Fires up an event and notifies all objects that subscribed and observe player events.
     * @param event event to be sent
     */
    public void notify(PlayerEvent event) {
        for(PlayerEventListener listener : listeners) {
            listener.onTick(event);
        }
    }
}
