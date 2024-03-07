package core.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller{

	private String url;

	public ForwardController(String url) {
		this.url = url;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return url;
	}
}
