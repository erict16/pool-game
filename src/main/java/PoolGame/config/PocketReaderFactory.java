package PoolGame.config;

public class PocketReaderFactory implements ReaderFactory{
    @Override
    public Reader buildReader() {
        return new PocketReader();
    }
}
