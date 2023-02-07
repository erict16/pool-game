package PoolGame.strategy;

public class OrangeStrategy extends PocketStrategy{
    public OrangeStrategy() {
        this.lives = 1;
    }
    @Override
    public void reset() {
        this.lives = 1;
    }
}
