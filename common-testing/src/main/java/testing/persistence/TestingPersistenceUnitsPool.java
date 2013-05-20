package testing.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import testing.internal.PoolProperties;

/**
 * Database pooling (not connection pooling) implementation for multi-threaded
 * tests with database.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 * 
 */
class TestingPersistenceUnitsPool {
    
    private final Map<String, EntityManagerFactory> factories;
    private final BlockingQueue<String> availablePersistenceUnits;
    private final PoolProperties poolProperties;
    
    public TestingPersistenceUnitsPool(String persistenceUnitName) {
        String testDBProfile = System.getProperty("testing.persistence.test-db-profile");
        String propertiesFile;
        if (testDBProfile == null || testDBProfile.isEmpty()) {
            propertiesFile = persistenceUnitName + ".properties";
        } else {
            propertiesFile = persistenceUnitName + "-" + testDBProfile + ".properties";
        }
        poolProperties = new PoolProperties(propertiesFile);
        
        if (poolProperties.getVariantCount() == 0) {
            throw new RuntimeException("There is no configuration for TestingPersistenceUnitsPool.\n"
                    + "Isn't " + propertiesFile + " empty");
        }
        this.factories = new HashMap<String, EntityManagerFactory>(poolProperties.getVariantCount());
        this.availablePersistenceUnits = new ArrayBlockingQueue<String>(poolProperties.getVariantCount());
        for (int variant = 0; variant < poolProperties.getVariantCount(); variant++) {
            String persistenceUnitVariant = persistenceUnitName + variant;
            poolProperties.setMapedName(persistenceUnitVariant, variant);
            Map<String, String> properties = poolProperties.getProperties(variant);
            factories.put(persistenceUnitVariant,
                    Persistence.createEntityManagerFactory(persistenceUnitName, properties));
            availablePersistenceUnits.add(persistenceUnitVariant);
        }
    }
    
    public void prepareTestingPersistenceUnit(TestingPersistenceUnit toPrepare) {
        try {
            String persistenceUnitNameVariant = availablePersistenceUnits.take();
            toPrepare.persistenceUnitNameVariant = persistenceUnitNameVariant;
            toPrepare.properties = poolProperties.getProperties(persistenceUnitNameVariant);
            toPrepare.entityManager = createEntityManager(persistenceUnitNameVariant);
            toPrepare.injectedEntityManager = createEntityManager(persistenceUnitNameVariant);
            toPrepare.clearDatabaseStrategy = poolProperties.getClearDatabaseStrategy();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public void freeTestingPersistenceUnit(TestingPersistenceUnit toFree) {
        try {
            if (toFree != null && toFree.entityManager != null) {
                toFree.entityManager.close();
            }
        } finally {
            try {
                if (toFree != null && toFree.injectedEntityManager != null) {
                    toFree.injectedEntityManager.close();
                }
            } finally {
                availablePersistenceUnits.add(toFree.persistenceUnitNameVariant);
            }
        }
    }
    
    private EntityManager createEntityManager(String persistenceUnitNameVariant) {
        return factories.get(persistenceUnitNameVariant).createEntityManager();
    }
}
