package testing.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import testing.internal.PoolProperties;

import com.google.code.liquidform.LiquidForm;


/**
 * Allows tests with real database and provide features:
 * <ul>
 * <li>cleanup of database, see: {@link ClearDatabaseStrategy}, clear.database.strategy property of
 * &lt;persistence-unit&gt;-test-db.properties
 * <li>prepare of test data, see: {@link #persist(Object...)}, {@link #persist(java.util.List)}
 * <li>check of database state, see: {@link #createQuery(Object)}, {@link LiquidForm}
 * <li>separation of {@link EntityManager} for objects under tests and tests prepare/check
 * <li>pooled access to many instances of databases for multi thread testing, see:
 * {@link TestingPersistenceUnitsPool}, {@link PoolProperties}
 * </ul>
 * 
 * <pre>
 * public class PersonRepositoryTest {
 *     
 *     &#064;Rule public TestingPersistenceUnit persistenceUnit = new TestingPersistenceUnit(&quot;address-book-test-db&quot;);
 *     
 *     &#064;Inject private PersonRepository personRepository;
 *     
 *     &#064;Before
 *     public void setUp() {
 *         Testing.inject(this);
 *     }
 *     
 *     &#064;Test
 *     public void shouldFindExistingPerson() throws Exception {
 *         // given
 *         PersonNumber personNumber = new PersonNumber(7);
 *         PersonEntity expectedPerson =
 *                 aPerson().likeBobFromLongstreet()
 *                         .withPersonNumber(personNumber).build();
 *         persistenceUnit.persist(expectedPerson,
 *                 aPerson().likeJohnFromHighstreet()
 *                         .withPersonNumber(1).build()
 *                 );
 *         
 *         // when
 *         PersonEntity foundPerson = personRepository.find(personNumber);
 *         
 *         // then
 *         assertThat(foundPerson)
 *                 .isNotSameAs(expectedPerson)
 *                 .isEqualTo(expectedPerson);
 *     }
 * }
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class TestingPersistenceUnit extends TestingPersistenceUnitImpl implements TestRule {
    
    final static Map<String, TestingPersistenceUnitsPool> pools = Collections.synchronizedMap(new HashMap<String, TestingPersistenceUnitsPool>(1));
    final String persistenceUnitName;
    
    String persistenceUnitNameVariant;
    Map<String, String> properties;
    EntityManager injectedEntityManager;
    ClearDatabaseStrategy clearDatabaseStrategy;
    
    public TestingPersistenceUnit(String testingPersistenceUnitName) {
        persistenceUnitName = testingPersistenceUnitName;
    }
    
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return injectedEntityManager.getEntityManagerFactory().getPersistenceUnitUtil();
    }
    
    public void begin() {
        injectedEntityManager.getTransaction().begin();
    }
    
    public void commit() {
        if (injectedEntityManager.getTransaction().isActive()) {
            injectedEntityManager.getTransaction().commit();
        }
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement,
     * org.junit.runner.Description)
     */
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (!pools.containsKey(persistenceUnitName)) {
                    synchronized (pools) {
                        if (!pools.containsKey(persistenceUnitName)) {
                            pools.put(persistenceUnitName, new TestingPersistenceUnitsPool(persistenceUnitName));
                        }
                    }
                }
                TestingPersistenceUnitsPool testingPersistenceUnitsPool = pools.get(persistenceUnitName);
                try {
                    testingPersistenceUnitsPool.prepareTestingPersistenceUnit(TestingPersistenceUnit.this);
                    entityManager.getTransaction().begin();
                    clearDatabaseStrategy.cleanDatabase(entityManager, properties);
                    entityManager.getTransaction().commit();
                    base.evaluate();
                } finally {
                    testingPersistenceUnitsPool.freeTestingPersistenceUnit(TestingPersistenceUnit.this);
                }
            }
        };
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TestingPersistenceUnit [persistenceUnitName=");
        builder.append(persistenceUnitName);
        builder.append("]");
        return builder.toString();
    }
}
