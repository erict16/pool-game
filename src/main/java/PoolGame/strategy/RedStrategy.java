package PoolGame.strategy;

public class RedStrategy extends PocketStrategy{
    public RedStrategy() {
        this.lives = 1;
    }
    @Override
    public void reset() {
        this.lives = 1;
    }
}
