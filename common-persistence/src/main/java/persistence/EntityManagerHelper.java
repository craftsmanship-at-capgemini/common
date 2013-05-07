package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Makes using of {@link EntityManager} little bit easier.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class EntityManagerHelper {
    
    public static <EntityType> EntityType findOne(EntityManager entityManager, Class<EntityType> entityClass, String query, QueryParamBuilder params) throws NotFoundException {
        TypedQuery<EntityType> queryObject = entityManager.createQuery(query, entityClass);
        for (String name : params.map.keySet()) {
            Object value = params.map.get(name);
            queryObject.setParameter(name, value);
        }
        try {
            return queryObject.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        }
    }
    
    public static <EntityType> List<EntityType> findMany(EntityManager entityManager, Class<EntityType> entityClass, String query, QueryParamBuilder params) {
        TypedQuery<EntityType> queryObject = entityManager.createQuery(query, entityClass);
        for (String name : params.map.keySet()) {
            Object value = params.map.get(name);
            queryObject.setParameter(name, value);
        }
        return queryObject.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public static <Type> Type findOneDynamicTyped(EntityManager entityManager, String query, QueryParamBuilder params) throws NotFoundException {
        Query queryObject = entityManager.createQuery(query);
        if (params != null) {
            for (String name : params.map.keySet()) {
                Object value = params.map.get(name);
                queryObject.setParameter(name, value);
            }
        }
        try {
            return (Type) queryObject.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <Type> List<Type> findManyDynamicTyped(EntityManager entityManager, String query, QueryParamBuilder params) {
        Query queryObject = entityManager.createQuery(query);
        if (params != null) {
            for (String name : params.map.keySet()) {
                Object value = params.map.get(name);
                queryObject.setParameter(name, value);
            }
        }
        return queryObject.getResultList();
    }
    
    private EntityManagerHelper() {
    }
    
}
