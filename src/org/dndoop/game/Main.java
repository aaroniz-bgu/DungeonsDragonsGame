package org.dndoop.game;

import org.dndoop.game.board.GameManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(System.out::println);
        Path currentDirectory = Paths.get("");
        Path parentDirectory = currentDirectory.toAbsolutePath().getParent();
        String levels_dir = "/levels_dir";
        //gameManager.start(args[0]);
        //TODO return to args[0] for jar I assume. Also can change this to match where levels_dir is in your folder
        //Its currently in the parent folder for me. We could change it so it's in the project though if you want.
        gameManager.start(""+parentDirectory+levels_dir);
    }
}
