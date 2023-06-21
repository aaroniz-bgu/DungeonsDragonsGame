package org.dndoop.game;

import org.dndoop.game.board.GameManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String LEVELS_DIRECTORY_NAME = "levels_dir";

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        Path currentDirectory = Paths.get("");
        Path parentDirectory = currentDirectory.toAbsolutePath()/*just to be safe i'll keep it here .getParent()*/;
        String levels_dir = "/";
        if(args.length == 0) {
            levels_dir += LEVELS_DIRECTORY_NAME;
        } else {
            levels_dir += args[0];
        }
        //Its currently in the parent folder for me. We could change it so it's in the project though if you want.
        gameManager.start(""+parentDirectory+levels_dir);
    }
}
