package core.ref;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.isAnnotationPresent(MyTest.class))
            .forEach(method -> {
				try {
					method.invoke(clazz.newInstance());
				} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
					throw new RuntimeException(e);
				}
			});
    }
}
