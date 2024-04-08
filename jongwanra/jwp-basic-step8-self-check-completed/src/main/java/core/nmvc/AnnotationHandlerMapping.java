package core.nmvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		// Reflection API를 활용해서 @RequestMapping Annotation이 붙은 Controller를 초기화한다.
		Reflections reflections = new Reflections(basePackage, new SubTypesScanner(false));

		Set<Class<?>> classSet = new HashSet<>(reflections.getSubTypesOf(Object.class));
		for (Class<?> aClass : classSet) {
			if (!aClass.isAnnotationPresent(Controller.class)) {
				continue;
			}

			Method[] methods = aClass.getMethods();
			for (Method method : methods) {
				if (!method.isAnnotationPresent(core.annotation.RequestMapping.class)) {
					continue;
				}

				RequestMapping annotation = method.getAnnotation(RequestMapping.class);
				String value = annotation.value();
				RequestMethod methodType = annotation.method();

				HandlerExecution handlerExecution = new HandlerExecution(method);
				handlerExecutions.put(new HandlerKey(value, methodType), handlerExecution);

			}
		}

	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
