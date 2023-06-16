package org.dndoop.game;

import org.dndoop.game.board.GameManager;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(System.out::println);
        gameManager.start(args[0]);
    }
}
