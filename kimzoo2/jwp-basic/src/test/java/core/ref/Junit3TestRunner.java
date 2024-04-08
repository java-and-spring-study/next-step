package core.ref;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().contains("test"))
            .forEach(method -> {
				try {
                    // 기본 생성자를 가지는 경우 newInstance로 실행 가능하다
					method.invoke(clazz.newInstance());
				} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
					throw new RuntimeException(e);
				}
			});

    }
}
