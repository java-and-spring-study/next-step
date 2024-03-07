package next;

import next.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private RequestMapping rm;


	@Override
	public void init() {
		rm = new RequestMapping();
		rm.init();

	}
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String requestUri = req.getRequestURI();
		Controller controller = rm.findController(requestUri);

		try{
			String viewName = controller.execute(req, resp);
			move(viewName, req, resp);
		}catch (Throwable e) {
			throw new ServletException(e.getMessage());
		}
	}

	// forward or redirect
	// redirect는 요청이 2번 발생한다.
	// forward는 요청이 1번 발생한다. => 응답을 전달한다.
	private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return;
		}

		RequestDispatcher rd = req.getRequestDispatcher(viewName);
		rd.forward(req, resp);
	}
}
