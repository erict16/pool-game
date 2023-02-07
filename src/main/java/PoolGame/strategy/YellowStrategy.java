package PoolGame.strategy;

public class YellowStrategy extends PocketStrategy{
    public YellowStrategy() {
        this.lives = 1;
    }
    @Override
    public void reset() {
        this.lives = 1;
    }
}
