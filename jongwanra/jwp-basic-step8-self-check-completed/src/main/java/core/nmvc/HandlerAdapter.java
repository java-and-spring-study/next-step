package core.nmvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.LegacyHandlerMapping;

public class HandlerAdapter {
	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;

	public void initialize() {
		lhm = new LegacyHandlerMapping();
		lhm.initialize();

		ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();
	}

	public Controller getHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Controller controller = lhm.findController(request.getRequestURI());
		if (isPresent(controller)) {
			return controller;
		}

		HandlerExecution handlerExecution = ahm.getHandler(request);
		if (isPresent(handlerExecution)) {
			return handlerExecution;
		}

		throw new ServletException("유효하지 않는 요청입니다.");

	}

	private boolean isPresent(Controller controller) {
		return controller != null;
	}
}
