package observer.publisher;

import java.io.File;

public class LogOpenlistener implements EventListener{
    private final File log;
    public LogOpenlistener(String fileName) {
        this.log = new File(fileName);
    }
    @Override
    public void update(String eventType, File file) {
        System.out.println("Save to log " + log +
                ": Someone has performed " + eventType +
                " operation with the following file: " +
                file.getName());
    }
}
