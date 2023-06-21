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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        CLI.endGameScreen(player.isAlive());
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
                CLI.setStatBar(player::getBar);
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
        List<String> sortedPaths = new ArrayList<>();
        List<String> filePaths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path), "*.txt")) {
            for (Path file : directoryStream) {
                if (Files.isRegularFile(file)) {
                    filePaths.add(file.toString());
                }
            }
            sortedPaths = filePaths.stream()
                    .sorted(Comparator.comparingInt(this::extractLevelNumber))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.levelPaths = sortedPaths;
    }

    private int extractLevelNumber(String filePath) {
        String fileName = new File(filePath).getName();
        int index = fileName.lastIndexOf("level") + "level".length();
        String levelNumber = fileName.substring(index, fileName.lastIndexOf('.'));
        return Integer.parseInt(levelNumber);
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

    /**
     * For testing purposes only.
     * @return CLI
     */
    public CLI getCLI () {
        return this.CLI;
    }

    /**
     * For testing purposes only.
     * @param player
     */
    public void setPlayer (Player player) {
        this.player = player;
    }

    /**
     * For testing purposes only.
     * @return NOTIFIER
     */
    public GameEventNotifier getNotifier() {
        return this.NOTIFIER;
    }

    /**
     * For testing purposes only.
     * @return factory
     */
    public TileFactory generateAndReturnFactory() {
        this.factory = new TileFactory(NOTIFIER, CLI.getCallback());
        return this.factory;
    }

    /**
     * For testing purposes only.
     * @return factory
     */
    public void setBoard(GameBoard board) {
        this.board = board;
    }

    /**
     * For testing purposes only.
     * @return factory
     */
    public TileFactory getFactory() {
        return this.factory;
    }
}
