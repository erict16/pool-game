package PoolGame.memento;

public class Undo {
    private String state;

    public Undo(String state) {
        this.state = state;
    }
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
