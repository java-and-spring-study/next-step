package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import next.dao.UserDao;

public class HomeController implements Controller {
	@Override
	public JspView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserDao userDao = new UserDao();
		req.setAttribute("users", userDao.findAll());
		return new JspView("home.jsp");
	}
}
