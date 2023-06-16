package org.dndoop.game.tile;

import org.dndoop.game.tile.tile_utils.Position;

public class Empty extends Tile {
    private static final Character EMPTY_CHARACTER = '.';
    public Empty(Position position) {
        super(EMPTY_CHARACTER, position);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
