package base.annotations;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PageId {

    public enum Condition{
        ELEMENT_PRESENTED
    }

    Condition condition() default Condition.ELEMENT_PRESENTED;

}
