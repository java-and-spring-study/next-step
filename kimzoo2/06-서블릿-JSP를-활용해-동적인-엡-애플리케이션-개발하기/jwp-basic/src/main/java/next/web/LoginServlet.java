package next.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		User foundUser = DataBase.findUserById(userId);
		if(foundUser == null) {
			resp.sendRedirect("/user/login_failed.html");
			return;
		}
		if(!foundUser.getPassword().equals(password)){
			resp.sendRedirect("/user/login_failed.html");
			return;
		}
		HttpSession session = req.getSession(true);
		session.setAttribute("user", foundUser);
		log.info("user = {}", foundUser);
		resp.sendRedirect("/user/list");
	}
}
