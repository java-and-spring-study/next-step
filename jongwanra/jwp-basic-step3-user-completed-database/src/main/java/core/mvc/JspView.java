package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private String uri;

	public JspView(String uri) {
		this.uri = uri;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (uri.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			String removedPrefix = uri.substring(DEFAULT_REDIRECT_PREFIX.length());
			System.out.println("removedPrefix = " + removedPrefix);
			response.sendRedirect(removedPrefix);
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher(uri);
		rd.forward(request, response);
	}
}
