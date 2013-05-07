package testing.persistence;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.code.liquidform.Parameters;

/**
 * Implementation of {@link TestingPersistenceUnit} testing tool.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class TestingPersistenceUnitImpl {
    
    protected EntityManager entityManager;
    
    public TestingPersistenceUnitImpl() {
        super();
    }
    
    public TestingPersistenceUnitImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Returns instance of EntityManager for test purpose - preparing state of database or checking
     * of after test.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    public void persist(List<?> entities) {
        persist(entities.toArray());
    }
    
    public void persist(Object... entities) {
        entityManager.getTransaction().begin();
        try {
            List<Object> toPersist = new LinkedList<Object>(Arrays.asList(entities));
            while (!toPersist.isEmpty()) {
                Object object = toPersist.get(0);
                toPersist.remove(0);
                if (object instanceof Collection<?>) {
                    toPersist.addAll((Collection<?>) object);
                } else {
                    entityManager.persist(object);
                }
            }
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    public Query createQuery(Object query) {
        Query createdQuery = entityManager.createQuery(query.toString());
        for (Entry<String, Object> param : Parameters.getMap().entrySet()) {
            createdQuery.setParameter(param.getKey(), param.getValue());
        }
        return createdQuery;
    }
    
}
