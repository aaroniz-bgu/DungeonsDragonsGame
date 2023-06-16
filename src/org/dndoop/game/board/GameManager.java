package org.dndoop.game.board;

import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.MessageCallback;
import org.dndoop.game.utils.events.GameEvent;
import org.dndoop.game.utils.events.GameEventListener;
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
    private Player player;
    private GameBoard board;
    private GameEventNotifier notifier;
    private List<String> levelPaths;
    //private Joystick joystick;
    //private CLI cli;
    private TileFactory factory;
    private MessageCallback m;
    public GameManager(MessageCallback m) {
        this.m = m;
        this.notifier = new GameEventNotifier();
    }

    /**
     * Entry point of the whole game, run it once.
     * @param path path to the levels_dir
     */
    public void start(String path) {
        this.factory = new TileFactory(notifier);

        loadLevels(path);
        runGame(path);
    }

    public void runGame(String path) {
        boolean chosen = false;
        Scanner input = new Scanner(System.in);
        List<Player> players = factory.listPlayers();
        while (!chosen) {
            int number = 1;
            System.out.println("Select player:");
            for (Player player : players)
            {
                System.out.println(""+number+". "+player.getDescription());
                number++;
            }
            String characterChoice = input.nextLine();
            try {
                Player player = factory.producePlayer(Integer.parseInt(characterChoice));
                this.player = player;
                chosen = true;
            }
            catch (Exception e)
            {

            }
        }
        input.close();
        for(String level : levelPaths) {
            this.board = new GameBoard(factory, player, level);
            System.out.println(board); //TODO remove this line, just for visual purposes when coding.
            runLevel();
        }
    }

    public void runLevel()
    {
        Scanner input = new Scanner(System.in);
        while(player.isAlive() && !board.getEnemies().isEmpty()) {
            String move = input.nextLine();
            //joystick.input(move);
            //CLI.updateDisplay();
        }
        input.close();
    }

    public void loadLevels(String path) {
        List<String> filePaths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path), "*.txt")) {
            for (Path file : directoryStream) {
                if (Files.isRegularFile(file)) {
                    filePaths.add(file.toString());
                }
            }
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

    }
}
