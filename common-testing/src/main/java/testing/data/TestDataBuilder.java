package testing.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Stereotype of test data builder utility classes.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestDataBuilder {
    
    Class<?> value();
}
