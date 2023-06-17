package org.dndoop.game.utils.events;

@FunctionalInterface
public interface Notifier {
    public Notifier notify(GameEvent event);
}
