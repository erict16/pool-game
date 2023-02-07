package PoolGame.config;

import PoolGame.GameManager;

import java.io.FileNotFoundException;

/** Interface for readers for sections of JSON. */
public interface Reader {
    /**
     * Parses the config file.
     * 
     * @param args
     * @param gameManager
     */
    public void parse(String args, GameManager gameManager) throws FileNotFoundException;
}
