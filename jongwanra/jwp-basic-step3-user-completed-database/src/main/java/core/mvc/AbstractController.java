package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController implements Controller {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return null;
	}

	protected ModelAndView jspView(String forwardUrl) {
		return new ModelAndView(new JspView(forwardUrl));
	}

	protected ModelAndView jsonView() {
		return new ModelAndView(new JsonView());
	}
}
