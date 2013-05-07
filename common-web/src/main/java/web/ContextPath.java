package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.faces.context.ExternalContext;
import javax.inject.Qualifier;

/**
 * Qualifier of request context path String.
 * 
 * @see ExternalContext#getRequestContextPath()
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
public @interface ContextPath {
}
