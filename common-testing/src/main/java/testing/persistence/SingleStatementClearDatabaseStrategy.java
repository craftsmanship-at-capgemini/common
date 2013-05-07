package testing.persistence;

import java.util.Map;

import javax.persistence.EntityManager;

/**
 * TODO MM Write comment to type SingleStatementClearDatabaseStrategy
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class SingleStatementClearDatabaseStrategy implements ClearDatabaseStrategy {
    
    /**
     * {@inheritDoc}
     * 
     * @see testing.persistence.ClearDatabaseStrategy#cleanDatabase(javax.persistence.EntityManager,
     * java.util.Map)
     */
    @Override
    public void cleanDatabase(EntityManager entityManager, Map<String, String> properties) {
        String statement = properties.get("clear.database.statement");
        entityManager.createNativeQuery(statement).executeUpdate();
    }
    
}
