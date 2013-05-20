package testing.persistence;

import java.util.Map;

import javax.persistence.EntityManager;

/**
 * Interface of database cleaning strategy. Strategy is used before each test
 * method.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public interface ClearDatabaseStrategy {
    
    static ClearDatabaseStrategy none = new ClearDatabaseStrategy() {
        @Override
        public void cleanDatabase(EntityManager entityManager, Map<String, String> properties) {
        }
    };
    
    void cleanDatabase(EntityManager entityManager, Map<String, String> properties);
}
