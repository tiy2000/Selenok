package core;

import java.lang.annotation.Annotation;

public class AnnotationFinder {

    public static <T extends Annotation> T findAnnotation(Class clazz, Class<T> annotationClass) {
        T annotation = (T) clazz.getAnnotation(annotationClass);
        if (annotation == null) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                return findAnnotation(superClass, annotationClass);
            }
        }
        return annotation;
    }
}
