package persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder helper of JPA Query named parameters.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class QueryParamBuilder {
    
    public static QueryParamBuilder withParams(int capacity) {
        return new QueryParamBuilder(capacity);
    }
    
    public static QueryParamBuilder withParam(String param, Object value) {
        return new QueryParamBuilder(1).param(param, value);
    }
    
    public static QueryParamBuilder withoutParams() {
        return new QueryParamBuilder(0);
    }
    
    Map<String, Object> map;
    
    private QueryParamBuilder(int capacity) {
        this.map = new HashMap<String, Object>(capacity);
    }
    
    public QueryParamBuilder param(String param, Object value) {
        map.put(param, value);
        return this;
    }
}
