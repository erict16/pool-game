package PoolGame.strategy;

public class GreenStrategy extends PocketStrategy {
    /** Creates a new blue strategy. */
    public GreenStrategy() {
        this.lives = 2;
    }
    public void reset() {
        this.lives = 2;
    }
}
