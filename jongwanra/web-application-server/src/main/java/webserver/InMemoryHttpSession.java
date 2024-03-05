package webserver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryHttpSession implements HttpSession {
    private Map<String, Object> storage = new ConcurrentHashMap<>();
    
    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void setAttribute(String name, Object value) {
        storage.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return storage.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        storage.remove(name);
    }

    @Override
    public void invalidate() {
        storage.clear();
    }
}
