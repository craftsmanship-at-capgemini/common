package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import javax.servlet.http.Cookie;

/**
 * Qualifier of Cookie value or {@link Cookie} object.
 * 
 * Read only cookie can by injected to String field or method parameter. Read
 * write cookie value can by injected as {@link Cookie} object.
 * 
 * <pre>
 * &#064;PageController(view = &quot;/details&quot;)
 * public class DetailsPageController {
 *     
 *     &#064;Inject @CookieParam(&quot;globalpreferences&quot;) String globelConfig;
 *     &#064;Inject @CookieParam(&quot;detailspreferences&quot;) Cookie detailsConfig;
 *     // ...
 * }
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
public @interface CookieParam {
    @Nonbinding
    public String value();
}
