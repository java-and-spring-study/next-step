package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.ModelAndView;

public class HandlerExecution implements Controller {
	private final Method method;

	public HandlerExecution(Method method) {
		this.method = method;
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (ModelAndView)method.invoke(method.getDeclaringClass().newInstance(), request, response);
	}
}
