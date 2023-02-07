package PoolGame.singleton;



public class TimeSingleton {
    private static TimeSingleton timeInstance = null;
    private TimeSingleton() {
    }

    public static TimeSingleton getInstance() {
        if (timeInstance == null) {
            timeInstance = new TimeSingleton();
        }
        return timeInstance;
    }

    public long getTimer() {
        return System.currentTimeMillis();
    }

}
