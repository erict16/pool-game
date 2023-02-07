package PoolGame.strategy;

public class BlackStrategy extends PocketStrategy {
    /** Creates a new blue strategy. */
    public BlackStrategy() {
        this.lives = 3;
    }
    @Override
    public void reset() {
        this.lives = 3;
    }
}
