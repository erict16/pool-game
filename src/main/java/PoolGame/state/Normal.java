package PoolGame.state;

import PoolGame.config.*;
import PoolGame.GameManager;

import java.io.FileNotFoundException;

public class Normal extends Level {
    public Normal(GameManager gameManager) {
        super(gameManager);
    }
    @Override
    public void loadGame() {
        String configPath = "src/main/resources/config_normal.json";
        ReaderFactory tableFactory = new TableReaderFactory();
        Reader tableReader = tableFactory.buildReader();
        try {
            tableReader.parse(configPath, gameManager);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        ReaderFactory ballFactory = new BallReaderFactory();
        Reader ballReader = ballFactory.buildReader();
        try {
            ballReader.parse(configPath, gameManager);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        // My code for reading pockets
        ReaderFactory pocketFactory = new PocketReaderFactory();
        Reader pocketReader = pocketFactory.buildReader();
        try {
            pocketReader.parse(configPath, gameManager);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
