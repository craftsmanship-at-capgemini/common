package testing;

import org.mockito.MockitoAnnotations;

/**
 * <p>
 * Entry point of some features of testing facility:
 * <ul>
 * <li>Creation and injection of mocks, spyes, captors and any Mockito features
 * <li>Creation and injection of real instances of business services with resolution of dependences
 * (injection of mocks, spyes, {@link javax.persistence.EntityManager}'s or other instances into
 * instances of business services)
 * <li>Creation and injection of {@link javax.persistence.EntityManager} with database pooling
 * </ul>
 * </p>
 * 
 * <h1>Unit testing</h1>
 * <p>
 * Build test context using standard Mockito annotations. Test context will by inject it into object
 * under test.<br>
 * <p>
 * <u>Note:</u> that {@link org.mockito.InjectMocks} annotation can by used instead
 * {@link javax.inject.Inject} but in this case no additional features will by available.
 * 
 * <pre>
 * public class PersonMaintenanceService_updatePersonTest {
 *     
 *     &#064;Mock private PersonRepository personRepository;
 *     &#064;Mock private PersonProviderService personProvider;
 *     &#064;Rule public ExpectedException thrown = ExpectedException.none();
 *     
 *     &#064;Inject private PersonMaintenanceService personMaintenance;
 *     
 *     &#064;Before
 *     public void setUp() {
 *         Testing.inject(this);
 *     }
 *     ...
 * }
 * </pre>
 * 
 * <h1>Integration testing</h1>
 * <p>
 * Build test context using {@link javax.inject.Inject} annotation and standard Mockito annotations.
 * Test context will by injected into objects under test (annotated with {@link javax.inject.Inject}
 * ).
 * </p>
 * <p>
 * Note into objects annotated with {@link org.mockito.InjectMocks} only mocks/spyes
 * <strong>NOT</strong> real instances are injected- use {@link javax.inject.Inject} annotation
 * instead.
 * </p>
 * 
 * <pre>
 * public class PersonMaintenanceService_updatePersonTest {
 *     
 *     &#064;Mock private PersonRepository personRepository;
 *     &#064;Inject private PersonProviderService personProvider;
 *     &#064;Rule public ExpectedException thrown = ExpectedException.none();
 *     
 *     &#064;Inject private PersonMaintenanceService personMaintenance;
 *     
 *     &#064;Before
 *     public void setUp() {
 *         Testing.inject(this);
 *     }
 *     ...
 * }
 * </pre>
 * 
 * <p>
 * Test context is the only difference between unit and integration test.
 * </p>
 * <h1>Persistence testing</h1>
 * <p>
 * Any unit test or integration test can by extended to test with real database by adding
 * {@link testing.persistence.TestingPersistenceUnit} rule. Use
 * {@link testing.persistence.TestingPersistenceUnit} rule to get persistence unit to one from
 * pooled databases and extend test context with instance of {@link javax.persistence.EntityManager}
 * Build further test context using {@link javax.inject.Inject} annotation and standard Mockito
 * annotations. Test context will by injected into objects under test (annotated with
 * {@link javax.inject.Inject}).
 * </p>
 * 
 * <pre>
 * public class PersonProviderService_getPersonTest {
 *     
 *     &#064;Rule public TestingPersistenceUnit persistenceUnit = new TestingPersistenceUnit(&quot;AddressBookTestPU&quot;);
 *     
 *     &#064;Inject private PersonProviderService personProvider;
 *     
 *     &#064;Before
 *     public void setUp() {
 *         Testing.inject(this);
 *     }
 *     ...
 * }
 * 
 * </pre>
 * 
 * <p>
 * Refer to {@link testing.persistence.TestingPersistenceUnit} documentation for description of
 * convenience methods. Refer to {@link testing.persistence.TestingPersistenceUnitsPool}
 * documentation for description of configuration of pooled databases.
 * </p>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class Testing {
    
    /**
     * Injects instances of {@link javax.persistence.EntityManager}'s, mocks, spyes and other
     * Mockito features into test instance and any underling instances (annotated with
     * {@link javax.inject.Inject})
     * 
     * @param test instance of JUnit test
     */
    public static void inject(Object test) {
        MockitoAnnotations.initMocks(test);
    }
    
    private Testing() {
    }
}
