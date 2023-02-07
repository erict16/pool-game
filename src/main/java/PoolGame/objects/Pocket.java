package PoolGame.objects;

import PoolGame.Config;

/** Holds information for all pocket-related objects. */
public class Pocket {

    private double xPosition;
    private double yPosition;
    private double radius;

    public Pocket(double xPosition, double yPosition, double radius) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.radius = radius;
    }

    /**
     * Returns the x position of the pocket.
     * 
     * @return x position.
     */
    public double getxPos() {
        return xPosition + Config.getTableBuffer();
    }

    /**
     * Returns the y position of the pocket.
     * 
     * @return y position.
     */
    public double getyPos() {
        return yPosition + Config.getTableBuffer();
    }

    /**
     * Returns the radius of the pocket.
     * 
     * @return radius.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Checks if the ball is in the pocket.
     * 
     * @param ball
     * @return true if ball is in pocket.
     */
    public boolean isInPocket(Ball ball) {
        double xDistance = Math.abs(ball.getxPos() - this.getxPos());
        double yDistance = Math.abs(ball.getyPos() - this.getyPos());
        double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return distance < radius;
    }
}
