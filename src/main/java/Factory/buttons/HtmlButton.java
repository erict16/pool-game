package factory.buttons;

public class HtmlButton implements Button{
    @Override
    public void render() {
        System.out.println("Test button");
    }

    @Override
    public void onClick() {
        System.out.println("HTML button test succeed!");
    }
}
