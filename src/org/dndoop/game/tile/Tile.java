package org.dndoop.game.tile;

public abstract class Tile {
    protected Character character;
    protected Position position;

    @Override
    public String toString() {
        return character.toString();
    }
  
    public Tile(Character character, Position position) {
        this.character = character;
        this.position = position;
    }

    protected Character getCharacter() {
        return this.character;
    }
    protected Position getPosition() {
        return this.position;
    }
    protected void setCharacter(Character character) {
        this.character = character;
    }
    protected void setPosition(Position position) {
        this.position = position;
    }
}
