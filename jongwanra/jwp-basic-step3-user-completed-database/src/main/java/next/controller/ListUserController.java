package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.UserDao;
import next.model.User;

public class ListUserController implements Controller {
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return new JspView("/user/login.jsp");
		}

		UserDao userDao = new UserDao();
		List<User> users = userDao.findAll();
		req.setAttribute("users", users);
		return new JspView("/user/list.jsp");
	}
}
