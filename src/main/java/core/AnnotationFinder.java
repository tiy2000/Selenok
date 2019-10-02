package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationFinder {

    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
        T annotation = (T) clazz.getAnnotation(annotationClass);
        if (annotation == null) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                return findAnnotation(superClass, annotationClass);
            }
        }
        return annotation;
    }

    public static Field findAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            return findAnnotatedField(superClass, annotationClass);
        }
        return null;
    }
}
