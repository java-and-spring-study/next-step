package core.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.support.context.ContextLoaderListener;

@WebServlet(name ="dispatcher",
	urlPatterns = "/", // 모든 요청을 하나의 서블릿으로 매핑할 수 있게 됨
	loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response) {
		RequestMapping mapper = new RequestMapping();
		logger.info("getRequestURI = {}", request.getRequestURI());
		String requestURI = request.getRequestURI();

		Controller controller = mapper.getController(requestURI);
		try {
			String responsePath = controller.execute(request, response);
			move(responsePath, request, response);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void move(String path, HttpServletRequest req, HttpServletResponse res) throws IOException,
		ServletException {
		String redirect = "redirect:";
		if(path.startsWith(redirect)){
			res.sendRedirect(path.substring(redirect.length()));
			return;
		}
		if(!path.endsWith(".jsp")){
			path = path + ".jsp";
		}
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, res);
	}
}
