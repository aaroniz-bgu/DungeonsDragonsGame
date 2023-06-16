package org.dndoop.game.board;

import org.dndoop.game.tile.Tile;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard implements GameEventListener, GetAtCallback {

    private final int WIDTH;
    private final int HEIGHT;

    private List<Tile> board;

    public GameBoard(int width, int height){
        board = new ArrayList<>();

        this.WIDTH = width;
        this.HEIGHT = height;

        //build game board here
    }

    /**
     * Returns a tile at the position provided.
     * @param position the position.
     * @return the tile at the position.
     */
    public Tile getAt(Position position) {
        for(Tile tile : board) {
            if(tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
    }

    /**
     * My most clever to string. -@aaroniz-bgu
     * @return A string representing the game board.
     */
    @Override
    public String toString() {
        String[][] build = new String[HEIGHT][WIDTH];
        for(Tile tile : board) {
            Position pos = tile.getPosition();
            int x = pos.getX();
            int y = pos.getY();

            build[y][x] = tile.toString();
        }

        String output = "";
        for(String[] s : build) {
            output = output.concat(Arrays.toString(s)+"\n");
        }

        return output;
    }

    @Override
    public void onTick(GameEvent event) {

    }
}
