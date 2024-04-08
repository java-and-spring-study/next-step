package core.ref;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        // 클래스 정보 출력하기
        // - 필드, 생성자, 메소드 정보를 출력한다.
        Arrays.stream(clazz.getFields())
            .forEach(prField -> logger.debug("public field = {}", prField.toString()));
        Arrays.stream(clazz.getDeclaredFields())
            .forEach(pubFiled -> logger.debug("private field = {}", pubFiled.toString()));
        Arrays.stream(clazz.getConstructors())
                .forEach(constructor -> logger.debug("constructor = {}", constructor.toString()));
        Arrays.stream(clazz.getMethods())
            .forEach(method -> logger.debug("method = {}", method.toString()));
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> constructor = Arrays.stream(constructors).findAny().get();
        //public User(String userId, String password, String name, String email) {
        User user = (User)constructor.newInstance("1L", "1234", "admin", "admin@admin.net");
        logger.debug("user = {}", user);
    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        String name = "주한";
        int age = 14;
        // 리플렉션 api 활용해서 값을 할당한다.
        Student student = new Student();
        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");
        nameField.setAccessible(true);
        ageField.setAccessible(true);
        nameField.set(student, name);
        ageField.setInt(student, age);
        logger.debug("student = {}", student);

        // getter를 통해 값을 확인한다.
        assertSame(student.getName(), name);
        assertSame(student.getAge(), age);

    }
}
