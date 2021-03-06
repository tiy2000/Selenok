package core;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectionUtils {

    @Nullable
    public static <T extends Annotation> T findClassAnnotation(Class<?> clazz, Class<T> annotationClass) {
        T annotation = (T) clazz.getAnnotation(annotationClass);
        if (annotation == null) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                return findClassAnnotation(superClass, annotationClass);
            }
        }
        return annotation;
    }

    @Nullable
    public static Annotation findClassAnnotations(Class<?> clazz, Class<?>... annotationClasses) {
        for (Class<?> annotationClass : annotationClasses) {
            Annotation annotation = clazz.getAnnotation((Class<? extends Annotation>) annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        Class superClass = clazz.getSuperclass();
        if (superClass != null) {
            return findClassAnnotations(superClass, annotationClasses);
        }
        return null;
    }

    @Nullable
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

    @Nullable
    public static Object getAnnotatedFieldValue(Object object, Class<? extends Annotation> annotationClass) {
        Field field = findAnnotatedField(object.getClass(), annotationClass);
        if (field != null) {
            return getFieldValue(field, object);
        }
        return null;
    }

    @Nullable
    public static Object getFieldValue(Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    @Nullable
    public static <T> T getFieldValue(Field field, Object target, Class<T> type) throws ClassCastException {
        try {
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
