package org.dndoop.game.utils.events;

@FunctionalInterface
public interface EventCallback {
    public void execute(GameEvent event);
}
