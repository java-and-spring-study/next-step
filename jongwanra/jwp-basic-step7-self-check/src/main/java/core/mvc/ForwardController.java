package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.model.User;

public class ForwardController extends AbstractController {
    private String forwardUrl;
    private boolean isRequiredAuth;

    public ForwardController(String forwardUrl, boolean isRequiredAuth) {
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
        this.forwardUrl = forwardUrl;
        this.isRequiredAuth = isRequiredAuth;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(isRequiredAuth) {
            User user = UserSessionUtils.getUserFromSession(request.getSession());
            if(user == null) {
                return jspView("/users/loginForm");
            }
        }
        return jspView(forwardUrl);
    }

}
