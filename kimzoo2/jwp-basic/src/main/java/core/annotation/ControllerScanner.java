package core.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

// 각 클래스의 인스턴스 생성을 담당한다.
public class ControllerScanner {

	public Map<Class<?>, Object> getControllers() throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections("core.nmvc");
		//주어진 어노테이션을 구현하는 모든 클래스와 인터페이스를 가져올 수 있다.
		return instantiateControllers(reflections.getTypesAnnotatedWith(Controller.class));
	}

	public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, Object> map = new HashMap<>();
		for (Class<?> aClass : annotated) {
			map.put(aClass, aClass.newInstance());
		}
		return map;
	}
}
