package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

/**
 * Stereotype of web view controller class.
 * 
 * <pre>
 * 
 * &#064;ViewController(view = &quot;/WEB-INF/templates/menu&quot;)
 * public class MenuViewController implements Serializable {
 *     
 *     private static final long serialVersionUID = -4071829444188899108L;
 *     
 *     &#064;Inject MenuViewControllerI18n i18n;
 *     &#064;Inject List&lt;MenuItem&gt; items;
 *     
 *     public MenuViewControllerI18n getI18n() {
 *         return i18n;
 *     }
 *     
 *     public List&lt;MenuItem&gt; getItems() {
 *         return items;
 *     }
 * }
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@RequestScoped
@Named
@Stereotype
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ViewController {
    public String view();
}
