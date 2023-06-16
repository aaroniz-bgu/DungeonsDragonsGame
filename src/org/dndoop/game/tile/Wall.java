package org.dndoop.game.tile;

import org.dndoop.game.tile.tile_utils.Position;

public class Wall extends Tile {
    private static final Character WALL_CHARACTER = '#';

    public Wall(Position position) {
        super(WALL_CHARACTER, position);
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
