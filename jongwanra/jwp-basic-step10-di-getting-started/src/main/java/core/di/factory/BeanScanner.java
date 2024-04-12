package core.di.factory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * BeanScanner
 * @Controller, @Service, @Repository Annotation이 붙은 Class들을 Set에 저장한다.
 */
public class BeanScanner {
    private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);
    private static final List<Class<? extends Annotation>> ANNOTATIONS_TO_SCAN = List.of(Controller.class, Service.class, Repository.class);

    private Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> getBeans() {
        Set<Class<?>> preInitiatedBeans = new HashSet<>();
        for (Class<? extends Annotation> annotationToScan : ANNOTATIONS_TO_SCAN) {
            preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotationToScan));
        }
        return preInitiatedBeans;
    }

}
