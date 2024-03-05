package webserver;

/**
 * 현재는 InMemory로 사용하겠지만,
 * 추후에 다른 Database로 확장할 가능성을 고려하여 interface를 선언했다.
 */
public interface HttpSession {

    String getId();

    void setAttribute(String name, Object value);

    Object getAttribute(String name);
    
    void removeAttribute(String name);

    // 현재 세션에 저장되어 있는 모든 값을 삭제
    void invalidate();
}
