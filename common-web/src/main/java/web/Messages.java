package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.enterprise.util.Nonbinding;
import javax.faces.application.FacesMessage;
import javax.inject.Qualifier;

/**
 * Qualifier of {@link FacesMessage} {@link Collection}.
 * 
 * <pre>
 * public class OrdersListPageControllerTest {
 *     &#064;Inject @Messages Collection&lt;FacesMessage&gt; globalMessages;
 *     &#064;Inject @Messages(forID = &quot;passwordField&quot;) Collection&lt;FacesMessage&gt; messages;
 *     
 *     public void printMessage() {
 *         FacesMessage message = new FacesMessage(&quot;some message&quot;);
 *         globalMessages.add(message);
 *     }
 * }
 * </pre>
 * 
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
public @interface Messages {
    @Nonbinding
    String forID() default "";
}
