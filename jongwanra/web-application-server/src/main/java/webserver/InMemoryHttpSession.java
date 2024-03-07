package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryHttpSession implements HttpSession {
    private String sessionId;
    private Map<String, Object> storage = new ConcurrentHashMap<>();


    public InMemoryHttpSession(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getId() {
        return sessionId;
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
