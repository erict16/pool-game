package PoolGame.strategy;

public class BrownStrategy extends PocketStrategy {
    /** Creates a new blue strategy. */
    public BrownStrategy() {
        this.lives = 3;
    }
    @Override
    public void reset() {
        this.lives = 3;
    }
}
