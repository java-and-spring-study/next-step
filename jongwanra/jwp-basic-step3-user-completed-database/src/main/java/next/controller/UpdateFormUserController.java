package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.UserDao;
import next.model.User;

public class UpdateFormUserController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userId = req.getParameter("userId");
		UserDao userDao = new UserDao();
		User user = userDao.findByUserId(userId);
		if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}
		req.setAttribute("user", user);
		return new ModelAndView(new JspView("/user/updateForm.jsp"));
	}
}
