package org.dndoop.game.board;

import org.dndoop.game.tile.Tile;
import org.dndoop.game.tile.tile_utils.Position;

@FunctionalInterface
public interface GetAtCallback {
    public Tile getAt(Position position);
}
