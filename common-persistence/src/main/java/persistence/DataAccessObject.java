package persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttributeType;

/**
 * Data Access Object stereotype.
 * 
 * Data Access Object provide read-only access to database model,
 * Business methods products view of database as transport objects.
 * Related topic: <strong>Command Query Responsibility Separation (CQRS)</strong>
 * 
 * @see Stateless
 * @see LocalBean
 * @see TransactionAttributeType#SUPPORTS
 * @see Repository
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DataAccessObject {
    
}
