package PoolGame.objects;

import PoolGame.strategy.*;

/** Builds pool balls. */
public class PoolBallBuilder implements BallBuilder {
    // Required Parameters
    private String colour;
    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double mass;

    // Variable Parameters
    private boolean isCue = false;
    public PocketStrategy strategy;

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    };

    @Override
    public void setxPos(double xPosition) {
        this.xPosition = xPosition;
    };

    @Override
    public void setyPos(double yPosition) {
        this.yPosition = yPosition;
    };

    @Override
    public void setxVel(double xVelocity) {
        this.xVelocity = xVelocity;
    };

    @Override
    public void setyVel(double yVelocity) {
        this.yVelocity = yVelocity;
    };

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    };

    /**
     * Builds the ball.
     * 
     * @return ball
     */
    public Ball build() {
        switch (colour) {
            case "white" -> {
                isCue = true;
                strategy = new BallStrategy();
            }
            // Lives 1
            case "orange" -> strategy = new OrangeStrategy();
            case "yellow" -> strategy = new YellowStrategy();
            case "red" -> strategy = new RedStrategy();
            // Lives 2
            case "green" -> strategy = new GreenStrategy();
            case "purple" -> strategy = new PurpleStrategy();
            case "blue" -> strategy = new BlueStrategy();
            // Lives 3
            case "black" -> strategy = new BlackStrategy();
            case "brown" -> strategy = new BrownStrategy();
            // Other balls
            default -> strategy = new BallStrategy();
        }

        return new Ball(colour, xPosition, yPosition, xVelocity, yVelocity, mass, isCue, strategy);
    }
}
