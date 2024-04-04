package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MyController {
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);

	@RequestMapping("/users")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response){
		logger.debug("users findUserId");
		return new ModelAndView(new JspView("/users/list.jsp"));

	}
}
