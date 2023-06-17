package org.dndoop.game.tile;

import org.dndoop.game.tile.tile_utils.Position;

public class Wall extends Tile {

    public Wall(Position position) {
        super('#', position);
    }

    /**
     * Visitor design pattern, visits the pattern later.
     * @param unit self.
     */
    @Override
    public void accept(Unit unit) {
        return; //Yeah.
    }
}
