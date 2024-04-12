package core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            if(beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> classToObjectMap = new HashMap<>();
        Set<Class<?>> classSet = beans.keySet();
        for (Class<?> aClass : classSet) {
            if(aClass.isAnnotationPresent(Controller.class)) {
                classToObjectMap.put(aClass, beans.get(aClass));
            };
        }
        return classToObjectMap;
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);

        // 종료 조건
        if(bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if(injectedConstructor == null) {
            bean = BeanUtils.instantiateClass(clazz);
            beans.put(clazz, bean);
            return bean;
        }

        logger.debug("injectedConstructor = {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;

    }



    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>(parameterTypes.length);
        // DI
        for (Class<?> clazz : parameterTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if(!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beans.get(concreteClazz);
            if(bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }

        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

}
