package web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JSF outcome builder. Redirects with GET request to page associated with
 * pageControllerClass. Given params are encoded in query part of URL.
 * 
 * <h1>Declaration of outcome method</h1>
 * 
 * <pre>
 * &#064;PageController(view = &quot;/details&quot;)
 * public class DetailsPageController {
 *     
 *     public static String outcome(PersonNumber personNumber) {
 *         return new Outcome(DetailsPageController.class)
 *                 .setParam(&quot;personNumber&quot;, personNumber).build();
 *     }
 *     // ...
 * }
 * </pre>
 * 
 * <h1>Usage of outcome method</h1>
 * 
 * <pre>
 * &#064;PageController(view = &quot;/edit&quot;)
 * &#064;ConversationScoped
 * public class EditPageController implements Serializable {
 *     
 *     public String submit() {
 *         // ...
 *         return DetailsPageController.outcome(personNumber);
 *     }
 * }
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class Outcome {
    
    public static String stayOnPage() {
        return null;
    }
    
    private final Class<?> viewControllerClass;
    private final Map<String, Object> params = new LinkedHashMap<String, Object>(0);
    
    public Outcome(Class<?> viewControllerClass) {
        this.viewControllerClass = viewControllerClass;
    }
    
    public Outcome setParam(String name, Object value) {
        params.put(name, value);
        return this;
    }
    
    public String build() {
        StringBuilder builder = new StringBuilder();
        try {
            
            if (viewControllerClass.isAnnotationPresent(PageController.class)) {
                builder.append(viewControllerClass.getAnnotation(PageController.class).view());
            } else {
                builder.append(viewControllerClass.getSimpleName().replaceAll("PageController$", ""));
            }
            builder.append("?faces-redirect=true");
            for (Entry<String, Object> entry : params.entrySet()) {
                builder.append("&amp; ").append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            // thrown only when "UTF-8" encoding is not supported by URLEncoder.encode() method
            throw new AssertionError(e);
        }
        return builder.toString();
    }
    
    @Override
    public String toString() {
        return build();
    }
    
}
