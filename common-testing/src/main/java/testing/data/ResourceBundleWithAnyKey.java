package testing.data;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.enterprise.inject.Alternative;

/**
 * Dummy implementation of {@link ResourceBundle} contains any key and returns
 * key String as value.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Alternative
public class ResourceBundleWithAnyKey extends ResourceBundle {
    
    @Override
    protected Object handleGetObject(String key) {
        return key;
    }
    
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(
                Collections.<String> emptySet());
    }
}
