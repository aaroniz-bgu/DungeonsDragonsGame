package org.dndoop.game.board;

import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.utils.MessageCallback;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;
import org.dndoop.game.utils.events.GameEventNotifier;

import java.util.List;

//TODO shouldn't be a singleton
public class GameManager implements GameEventListener {

    private List<String> levels;
    private List<Enemy> enemies;
    private Player player;

    private GameBoard board;
    private GameEventNotifier notifier;

    private MessageCallback m;

    public GameManager(MessageCallback m) {
        this.m = m;
        this.notifier = new GameEventNotifier();
        //something...something...getLevels...
        //runGame();
    }

    public void start(String path) {

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
