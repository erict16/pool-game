package factory.buttons;

/**
 * Base factory class. Note that "Factory" is merely a role for the CLASS.
 * It should have some core business logic which needs different product
 * to be created.
 */

public abstract class Dialog {
    public void renderWindow() {
        Button okButton = createButton();
        okButton.render();
    }

    /**
     * Subclass will override this method to create
     * specific button
     * @return
     */
    public abstract Button createButton();
}
