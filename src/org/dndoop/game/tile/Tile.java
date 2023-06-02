package org.dndoop.game.tile;

public abstract class Tile {
    protected Character character;
    protected Position position;

    @Override
    public String toString() {
        return character.toString();
    }
}
