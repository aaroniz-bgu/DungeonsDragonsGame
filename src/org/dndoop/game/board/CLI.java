package org.dndoop.game.board;

import org.dndoop.game.utils.MessageCallback;

/**
 * Display object for CLI presentation.
 */
public class CLI {

    //Used for invoking prints outside this object.
    private MessageCallback m;
    private GameBoard gameBoard;

    public CLI(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.m = this::print;
    }

    /**
     * @return returns a functional callback for other entities to use in the game.
     */
    public MessageCallback getCallback() {
        return m;
    }

    /**
     * When we're updating the level we need to get the new GameBoard accordingly.
     * @param gameBoard The new GameBoard.
     */
    public void nextLevel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        update();
    }

    /**
     * Updates the display.
     */
    public void update() {
        print(gameBoard.toString());
    }

    /**
     * Everything must go through here.
     * @param s The message to be displayed.
     */
    private void print(String s) {
        System.out.println(s);
    }
}
