package org.dndoop.game.board;

import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.Tile;
import org.dndoop.game.tile.Unit;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.events.EventCallback;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.files.TileFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameBoard implements GameEventListener, GetAtCallback {

    private final Map<GameEventName, EventCallback> events;

    private final int WIDTH;
    private final int HEIGHT;

    private final Character PLAYER_CHAR = '@';
    private final Character WALL_CHAR = '#';

    private List<Enemy> enemies;
    private List<Tile> board;

    public GameBoard(TileFactory factory, Player player, String levelPath){
        int[] widthAndHeight = createBoard(factory,player,levelPath);
        this.WIDTH = widthAndHeight[0];
        this.HEIGHT = widthAndHeight[1];
        events = new HashMap<>();
        buildMapEvents();
    }

    private int[] createBoard(TileFactory factory, Player player, String levelPath)
    {
        board = new ArrayList<>();
        int x = 0;
        int y = 0;
        int[] widthAndHeight = new int[2];
        enemies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(levelPath))) {
            int character;
            while ((character = reader.read()) != -1) {
                char c = (char) character;
                Position position = new Position(x, y);
                switch (c) {
                    case '.':
                        board.add(factory.produceEmpty(position));
                        break;
                    case '#':
                        board.add(factory.produceWall(position));
                        break;
                    case '@':
                        player.setPosition(position);
                        player.setTilesAccess(this);
                        board.add(player);
                        break;
                    case '\r':
                        break;
                    case '\n':
                        // Newline encountered, increment y, reset x.
                        y++;
                        widthAndHeight[0] = x;
                        x = 0;
                        break;
                    default: //enemy
                        Enemy enemy = factory.produceEnemy(c, position, this);
                        enemies.add(enemy);
                        board.add(enemy);
                        break;
                }
                if (!(c == '\n' || c == '\r'))
                    x++;
            }
            widthAndHeight[1] = y+1; //Since indexing starts at 0.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return widthAndHeight;
    }

    public List<Enemy> getEnemies() {
        return enemies;
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
            output = output.concat(
                    Arrays.toString(s)
                            .replace("[","")
                            .replace("]","")
                            .replace(", ","")
                            +"\n");
        }

        return output;
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if(events.containsKey(event.getName())) {
            events.get(event.getName()).execute(event);
        }
    }

    private void buildMapEvents() {
        events.put(GameEventName.ENEMY_DEATH_EVENT, (GameEvent event) -> {
            getEnemies().remove(event.getActor());
            board.remove(event.getActor());
            board.add(new Empty(event.getPosition()));
        });

    }
}
