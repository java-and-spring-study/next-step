package next.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

	default String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	};

}
