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
		User foundUser = DataBase.findUserById(userId);
		if(foundUser == null)
			throw new IllegalArgumentException("아이디나 비밀번호가 틀렸습니다.");
		HttpSession session = req.getSession(false);
		session.setAttribute("user", foundUser);
		log.info("user = {}", foundUser);
		resp.sendRedirect("/user/list");
	}
}
