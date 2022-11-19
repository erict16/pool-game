package builder.builders;

public class Demo {
    public static void main(String[] args) {
        CarBuilder builder = new CarBuilder();
        Director director = new Director();
        director.constructCityCar(builder);
        Car car = builder.getResult();
        System.out.println("Car built:\n" + car.getCarType());
    }
}
