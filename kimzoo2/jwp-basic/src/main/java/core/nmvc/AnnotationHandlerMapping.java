package core.nmvc;

import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.el.util.ReflectionUtil;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.ControllerScanner;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping implements HandlerMapping{
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        // @Controller 어노테이션이 붙은 클래스를 찾는다.
		Map<Class<?>, Object> map = null;
		try {
			map = controllerScanner.getControllers();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		// 그 클래스 중에 @RequestMapping 어노테이션이 존재하는 메소드를 찾는다.
        Set<Class<?>> classes = new HashSet<>(map.keySet());
        Set<Method> requestMappingMethods = getRequestMappingMethods(classes);
        // map에 메소드에 붙은 @RequestMapping 어노테이션과 메소드 정보를 담는다.
        for (Method method : requestMappingMethods) {
            // {annotation, methodInform}
            HandlerKey handlerKey = createHandlerKey(method.getAnnotation(RequestMapping.class));
            // 메소드 기반으로 클래스를 찾고 map의 인스턴스를 찾는다.
            Object instance = map.get(method.getDeclaringClass());
            HandlerExecution handlerExecution =
                new HandlerExecution(instance, method);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        // handlerExecutions map에 요청 url와 http메소드로 등록된 HandlerKey가 등록되어 있는지 확인한다
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    public Set<Method> getRequestMappingMethods(Set<Class<?>> clazz){
        Set<Method> allMethods = new HashSet<>();
        for (Class<?> aClass : clazz) {
            allMethods = ReflectionUtils.getAllMethods(aClass,
                ReflectionUtils.withAnnotation(RequestMapping.class));
        }
        return allMethods;
    }

    private HandlerKey createHandlerKey(RequestMapping rm){
        return new HandlerKey(rm.value(), rm.method());
    }
}
