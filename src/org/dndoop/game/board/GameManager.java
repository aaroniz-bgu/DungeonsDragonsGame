package org.dndoop.game.board;

import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;

import java.util.List;

public class GameManager implements GameEventListener {

    private List<String> levels;
    private List<Enemy> enemies;
    private GameBoard board;
    private Player player;

    private static GameManager instance;
    private GameManager(){
        //runGame();
    }

    /**
     * Singleton, used to get the game's manager
     * @return Instance of game manager.
     */
    public static GameManager getInstance(){
        if(instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void runGame() {

    }

    public void loadLevels() {

    }

    public GameBoard getGameBoard() {
        return board;
    }

    @Override
    public void onTick(GameEvent event) {

    }
}
