package core.annotations;

import core.url.PageUrl;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PagePath {

    boolean loadable() default true;

    Class<? extends PageUrl.Template>[] templates() default {};

}
