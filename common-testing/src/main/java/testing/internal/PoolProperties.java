package testing.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import testing.persistence.ClearDatabaseStrategy;

/**
 * TODO MM Write comment to type PoolProperties
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class PoolProperties {
    
    private Properties properties;
    private List<Map<String, String>> variantProperties;
    private ClearDatabaseStrategy clearDatabaseStrategy = ClearDatabaseStrategy.none;
    private Map<String, Integer> mappedNames = new HashMap<String, Integer>();;
    
    static public Map<String, String> getSubset(String withPrefix, Map<String, String> from) {
        String prefix = withPrefix + ".";
        
        Map<String, String> subset = new HashMap<String, String>();
        for (Entry<String, String> entry : from.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                subset.put(entry.getKey().substring(prefix.length()), entry.getValue());
            }
        }
        return subset;
    }
    
    public PoolProperties(String path) {
        properties = load(path);
        variantProperties = interpret();
    }
    
    public Map<String, String> getProperties(int variant) {
        return variantProperties.get(variant);
    }
    
    public Map<String, String> getProperties(String variant) {
        return variantProperties.get(mappedNames.get(variant));
    }
    
    public int getVariantCount() {
        return variantProperties.size();
    }
    
    public ClearDatabaseStrategy getClearDatabaseStrategy() {
        return clearDatabaseStrategy;
    }
    
    public void setMapedName(String persistenceUnitVariant, int variant) {
        mappedNames.put(persistenceUnitVariant, variant);
    }
    
    private Properties load(String path) throws RuntimeException {
        Properties properties;
        try {
            properties = new Properties();
            InputStream ressource = PoolProperties.class.getClassLoader().getResourceAsStream(path);
            if (ressource == null) {
                throw new IOException();
            }
            properties.load(ressource);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Can't read pool configuration: file " + path + " is not present in test classpath", e);
        }
    }
    
    private List<Map<String, String>> interpret() {
        Map<String, String> commonProperties = new HashMap<String, String>();
        List<Map<String, String>> specificProperties = new LinkedList<Map<String, String>>();
        Pattern p = Pattern.compile("^(.+)\\.(\\d+)$");
        for (Object keyO : properties.keySet()) {
            String key = keyO.toString();
            Matcher m = p.matcher(key);
            if (m.matches()) {
                String basekey = m.group(1);
                int set = Integer.parseInt(m.group(2));
                while (specificProperties.size() < set + 1) {
                    specificProperties.add(new HashMap<String, String>());
                }
                specificProperties.get(set).put(basekey, properties.getProperty(key));
            } else {
                commonProperties.put(key, properties.getProperty(key));
                if (key.equals("clear.database.strategy") && !properties.getProperty(key).isEmpty()) {
                    try {
                        clearDatabaseStrategy = (ClearDatabaseStrategy) Class.forName(properties.getProperty(key)).newInstance();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Class defined as clear.database.strategy not in classpath", e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException("Class defined as clear.database.strategy has no default constructor", e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Class defined as clear.database.strategy is not public or has no public constructor", e);
                    } catch (ClassCastException e) {
                        throw new RuntimeException("Class defined as clear.database.strategy not implements testing.persistencelayer.ClearDatabaseStrategy", e);
                    }
                }
            }
        }
        if (specificProperties.isEmpty()) {
            specificProperties.add(new HashMap<String, String>());
            specificProperties.get(0).putAll(commonProperties);
        } else {
            for (Map<String, String> specific : specificProperties) {
                specific.putAll(commonProperties);
            }
        }
        return specificProperties;
    }
}
