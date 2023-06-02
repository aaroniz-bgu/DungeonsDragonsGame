package org.dndoop.game.tile;

public class Position {

    private int x;
    private int y;
    
    public Position(int initX, int initY){
        this.x = initX;
        this.y = initY;
    }

    public void setX(int x) {
        if(x >= 0)
            this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double range(Position to) {
        return Math.sqrt(Math.pow(x - to.getX(), 2) + Math.pow(y - to.getY(), 2));
    }

    /**
     * These methods are called only within units, after making sure the direction
     * chosen is legal. Would be better to not use them rn.
     */
    public void moveUp() {
        this.y+=1;
    }
    public void moveDown() {
        this.y-=1;
    }
    public void moveRight() {
        this.x+=1;
    }
    public void moveLeft() {
        this.x-=1;
    }


}
