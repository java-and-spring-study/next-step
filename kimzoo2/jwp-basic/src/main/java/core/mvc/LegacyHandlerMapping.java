package core.mvc;

import static core.mvc.Configuration.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import core.nmvc.HandlerMapping;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(LegacyHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", homeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());
        mappings.put("/qna/form", new ForwardController("/qna/form.jsp"));
        mappings.put("/qna/show", showController());
        // DispatcherServlet에서 이미 생성된 controller에 dao 주입하기엔 불편함..
        mappings.put("/qna/deleteQuestion", deleteQuestionController());
        mappings.put("/api/qna/list", apiShowController());
        mappings.put("/api/qna/addAnswer", addAnswerController());
        mappings.put("/api/qna/deleteAnswer", deleteAnswerController());
        mappings.put("/api/qna/deleteQuestion", apiDeleteQuestionController());

        logger.info("Initialized Request Mapping!");
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return mappings.get(requestUri);
    }
}
