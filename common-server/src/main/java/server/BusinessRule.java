package server;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Business rule stereotype.
 * 
 * @see BusinessRuleSet
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface BusinessRule {
    
}
