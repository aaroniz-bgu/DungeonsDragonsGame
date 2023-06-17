package org.dndoop.game.board;

import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.MessageCallback;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;
import org.dndoop.game.utils.events.GameEventName;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.files.TileFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO shouldn't be a singleton
public class GameManager implements GameEventListener {

    private List<String> levels;
    private List<String> levelPaths;
    private final GameEventNotifier NOTIFIER;

    private TileFactory factory;
    private final Scanner input;

    private Player player;
    private Joystick joystick;

    private GameBoard board;
    private CLI CLI;
    public GameManager() {
        this.CLI = new CLI(null);
        this.joystick = new Joystick(null);
        this.NOTIFIER = new GameEventNotifier().addListener(this);

        this.input = new Scanner(System.in);
    }

    /**
     * Entry point of the whole game, run it once.
     * @param path path to the levels_dir
     */
    public void start(String path) {
        this.factory = new TileFactory(NOTIFIER, CLI.getCallback());

        loadLevels(path);
        runGame(path);
        input.close();
    }

    /**
     * Iterates through all the different levels
     * @param path
     */
    public void runGame(String path) {
        choosePlayer();
        for(String level : levelPaths) {
            if(board != null) {
                NOTIFIER.removeListener(board);
            }
            this.board = new GameBoard(factory, player, level);
            NOTIFIER.addListener(board);

            CLI.nextLevel(board);
            runLevel();
            if(!player.isAlive()) break;
        }
    }

    /**
     * Lets the user choose a player he wants to play with.
     */
    public void choosePlayer() {
        boolean chosen = false;
        List<Player> players = factory.listPlayers();

        while(!chosen) {
            CLI.displayPlayerMenu(players);
            String characterChoice = input.nextLine();
            try {
                Player player = factory.producePlayer(Integer.parseInt(characterChoice));
                this.player = player;
                joystick.setPlayer(player);
                chosen = true;
            } catch (Exception e) {
                //User is idiot.
            }
        }
    }

    public void runLevel() {
        while(player.isAlive() && !board.getEnemies().isEmpty()) {



            String move = input.nextLine();
            joystick.input(move);
            CLI.update();
        }
    }

    /**
     * Loads the file paths of levels to be run.
     * @param path The path to the level directory
     */
    public void loadLevels(String path) {
        List<String> filePaths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path), "*.txt")) {
            for (Path file : directoryStream) {
                if (Files.isRegularFile(file)) {
                    filePaths.add(file.toString());
                }
            }
            //TODO Sorts by level_name in lexicographic order
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.levelPaths = filePaths;
    }

    public GameBoard getGameBoard() {
        return board;
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if(event.getName().equals(GameEventName.ENEMY_DEATH_EVENT)) {
            NOTIFIER.removeListener(event.getActor());
        }
    }
}
