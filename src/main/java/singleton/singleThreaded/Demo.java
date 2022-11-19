package singleton.singleThreaded;

public class Demo {
    public static void main(String[] args) {
        System.out.println("If you see the same value, then singleton was reused\n" +
                "if you see different value, then 2 singleton was created.");
        Singleton singleton = Singleton.getInstance("Hello");
        Singleton anotherSingleton = Singleton.getInstance("World!");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
