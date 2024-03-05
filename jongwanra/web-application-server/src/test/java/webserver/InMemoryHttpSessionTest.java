package webserver;

import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryHttpSessionTest {

    private HttpSession httpSession;

    @Before
    public void setUp() {
        System.out.println("=== before ===");
        httpSession = new InMemoryHttpSession();
    }

    @Test
    public void getId() {

        String uuid = httpSession.getId();
        assertThat(uuid).isNotNull();
    }

    @Test
    public void setAttribute() {

        String sessionId = httpSession.getId();
        User user = new User("test-user-id", "test-password", "test-name", "test@email.com");
        httpSession.setAttribute(sessionId, user);


        assertThat(httpSession.getAttribute(sessionId)).isEqualTo(user);
    }


    @Test
    public void removeAttribute() {
        // given
        final String sessionId = httpSession.getId();
        User user = new User("test-user-id", "test-password", "test-name", "test@email.com");

        httpSession.setAttribute(sessionId, user);
        assertThat(httpSession.getAttribute(sessionId)).isEqualTo(user);

        // when
        httpSession.removeAttribute(sessionId);

        // then
        assertThat(httpSession.getAttribute(sessionId)).isNull();

    }


    @Test
    public void invalidate() {
        // given
        final int numberOfSessionId = 5;
        List<String> sessionIds = new ArrayList<>(numberOfSessionId);
        for (int index = 0; index < numberOfSessionId; index++) {
            final String sessionId = httpSession.getId();
            User user = new User("test-user-id" + index, "test-password" + index, "test-name" + index, "test@email.com" + index);

            httpSession.setAttribute(sessionId, user);
            sessionIds.add(sessionId);
        }

        for (String sessionId : sessionIds) {
            assertThat(httpSession.getAttribute(sessionId)).isNotNull();
        }

        // when
        httpSession.invalidate();

        // then
        for (String sessionId : sessionIds) {
            assertThat(httpSession.getAttribute(sessionId)).isNull();
        }

    }

}
