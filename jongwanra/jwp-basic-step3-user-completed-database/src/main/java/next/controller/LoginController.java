package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements Controller {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		UserDao userDao = new UserDao();
		User user = userDao.findByUserId(userId);
		if (user == null) {
			req.setAttribute("loginFailed", true);
			return new ModelAndView(new JspView("/user/login.jsp"));
		}
		if (user.matchPassword(password)) {
			HttpSession session = req.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
			System.out.println("session = " + session);
			return new ModelAndView(new JspView("redirect:/"));
		} else {
			req.setAttribute("loginFailed", true);
			return new ModelAndView(new JspView("/user/login.jsp"));
		}
	}
}
