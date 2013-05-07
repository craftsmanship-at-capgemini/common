package testing.persistence;

import java.util.Map;

import javax.persistence.EntityManager;

/**
 * TODO MM Write comment to type ClearDatabaseStrategy
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
