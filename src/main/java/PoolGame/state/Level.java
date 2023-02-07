package PoolGame.state;

import PoolGame.GameManager;

public abstract class Level {
    protected final GameManager gameManager;
    protected Level(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public abstract void loadGame();
}
