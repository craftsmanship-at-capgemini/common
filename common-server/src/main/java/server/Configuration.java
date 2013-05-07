package server;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualifier of configuration values.
 * <p>
 * Example of configuration injection:
 * 
 * <pre>
 * public class OrderSchedulerService implements OrderProgressManagementRemote {
 *     &#064;Inject @Configuration Set&lt;String&gt; operators;
 * }
 * </pre>
 * 
 * <p>
 * Example of configuration production:
 * 
 * <pre>
 * &#064;ApplicationScoped
 * public class ConfigurationFactory {
 *     &#064;Produces
 *     &#064;Configuration
 *     Set&lt;String&gt; createSet(InjectionPoint injectionPoint) {
 *         String key = injectionPoint.getBean().getBeanClass().getName() +
 *                 injectionPoint.getMember().getName();
 *         Set&lt;String&gt; set = new HashSet&lt;String&gt;(
 *                 Arrays.asList(properties.getProperty(key).split(&quot;,&quot;)));
 *         return set;
 *     }
 * }
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
public @interface Configuration {
}
