package org.dndoop.game.tile.tile_utils;

public class Position {

    private int x;
    private int y;
    
    public Position(int initX, int initY){
        this.x = initX;
        this.y = initY;
    }

    /**
     * Copying constructor (Deep-copy).
     * @param toCopy
     */
    public Position(Position toCopy) {
        this.x = toCopy.getX();
        this.y = toCopy.getY();
    }

    public void setX(int x) {
        if(x >= 0)
            this.x = x;
    }

    public void setY(int y) {
        if(y >= 0)
            this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Used to calculate the distance to another position
     * @param to - The position we want to calculate distance to
     * @return the distance value
     */
    public double range(Position to) {
        return Math.sqrt(Math.pow(x - to.getX(), 2) + Math.pow(y - to.getY(), 2));
    }

    public void swapPositions(Position with) {
        int oldX = this.x, oldY = this.y;

        setX(with.getX());
        setY(with.getY());

        with.setX(oldX);
        with.setY(oldY);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    //Just me casually escaping using instanceof
    public boolean equals(Position o) {
        return x == o.getX() && y == o.getY();
    }

    /**
     * Gets the position you're trying to move to, doesn't move you to it.
     * You're only moving by switching with empty tiles.
     */
    public Position move(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }
}
