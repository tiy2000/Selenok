package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectionUtils {

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

    public static Object getAnnotatedFieldValue(Object object, Class<? extends Annotation> annotationClass) {
        Field field = findAnnotatedField(object.getClass(), annotationClass);
        if (field != null) {
            return getFieldValue(field, object);
        }
        return null;
    }

    public static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    public static <T> T getFieldValue(Field field, Class<T> clazz, Object object) throws ClassCastException {
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }
}
