package org.dndoop.game.utils;

@FunctionalInterface
public interface MessageCallback{
    void send(String message);
}