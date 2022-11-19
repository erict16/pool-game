package builder.builders;

public class CarBuilder implements Builder {
    private CarType type;
    private int seats;
    private Engine engine;
    @Override
    public void setCarType(CarType type) {
        this.type = type;
    }

    @Override
    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Car getResult() {
        return new Car(type, seats, engine);
    }
}
