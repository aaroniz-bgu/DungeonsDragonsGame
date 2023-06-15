package org.dndoop.game.tile;

import org.dndoop.game.tile.tile_utils.Position;

public class Empty extends Tile {
    public Empty(Position position) {
        super('.', position);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
