package singleton.naiveSingleton;


/**
 * The same class behaves incorrectly in a multithreaded environment.
 * Multiple threads can call the creation method simultaneously and get
 * several instance of Singleton class.
 */
public class Singleton {
    private static Singleton instance;
    public String value;

    private Singleton(String value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        if (instance == null) {
            instance = new Singleton(value);
        }
        return instance;
    }
}
