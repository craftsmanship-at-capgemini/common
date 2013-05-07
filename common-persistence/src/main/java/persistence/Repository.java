package persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Repository stereotype.
 * 
 * Repository provide read-write access to instances of particular entity.
 * Related topic: <strong>Command Query Responsibility Separation (CQRS)</strong>
 * 
 * @see Stateless
 * @see LocalBean
 * @see DataAccessObject
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Repository {
    
}
