package PoolGame.observer;

public class ScoreObserver implements Observer{
    private int score;
    public ScoreObserver() {
        this.score = 0;
    }
    @Override
    public void updateScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
