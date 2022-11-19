package observer.publisher;

import java.io.File;

public interface EventListener {
    void update(String eventType, File file);
}
