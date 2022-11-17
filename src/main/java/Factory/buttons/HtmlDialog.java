package factory.buttons;

/**
 * HTML button concrete factory
 */
public class HtmlDialog extends Dialog{
    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
