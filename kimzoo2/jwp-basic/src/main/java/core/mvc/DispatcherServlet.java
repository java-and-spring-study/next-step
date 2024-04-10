package core.mvc;

import static com.sun.javafx.media.PrismMediaFrameHandler.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private LegacyHandlerMapping rm;
	private AnnotationHandlerMapping ahm;
	private List<HandlerMapping> hm;

	@Override
	public void init() throws ServletException {
		LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
		AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("core.nmvc");
		legacyHandlerMapping.initMapping();
		annotationHandlerMapping.initialize();
		hm.add(legacyHandlerMapping);
		hm.add(annotationHandlerMapping);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
		Object handler = getHandler(req);
		ModelAndView mav = null;
		try {
			if (handler instanceof Controller) {
				mav = ((Controller)handler).execute(req, resp);
			} else if (handler instanceof HandlerExecution) {
				mav = ((HandlerExecution)handler).handle(req, resp);
			}
			render(req, resp, mav);
		} catch (Exception e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}

	private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
		View view = mav.getView();
		view.render(mav.getModel(), req, resp);
	}
}
