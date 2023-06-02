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

    public Character getCharacter() {
        return this.character;
    }
    public Position getPosition() {
        return this.position;
    }
    public void setCharacter(Character character) {
        this.character = character;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}
