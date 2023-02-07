package PoolGame.strategy;

public class BlueStrategy extends PocketStrategy {
    /** Creates a new blue strategy. */
    public BlueStrategy() {
        this.lives = 2;
    }
    @Override
    public void reset() {
        this.lives = 2;
    }
}
