package testing.internal;

import static org.mockito.internal.util.StringJoiner.join;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Rule;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.exceptions.Reporter;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.configuration.DefaultAnnotationEngine;
import org.mockito.internal.configuration.InjectingAnnotationEngine;
import org.mockito.internal.configuration.injection.MockInjection;
import org.mockito.internal.util.reflection.FieldInitializationReport;
import org.mockito.internal.util.reflection.FieldInitializer;
import org.mockito.internal.util.reflection.FieldSetter;

import testing.persistence.TestingPersistenceUnit;


/**
 * Implementation of {@link AnnotationEngine} extended of injection of instances (fields annotated
 * with {@link Inject}) and {@link EntityManager}.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class ExtendedAnnotationEngine implements AnnotationEngine {
    
    AnnotationEngine delegate = new InjectingAnnotationEngine();
    
    /***
     * Create a mock using {@link DefaultAnnotationEngine}
     * 
     * @see org.mockito.internal.configuration.DefaultAnnotationEngine
     * @see org.mockito.configuration.AnnotationEngine#createMockFor(java.lang.annotation.Annotation,
     * java.lang.reflect.Field)
     */
    @Override
    @SuppressWarnings("deprecation")
    public Object createMockFor(Annotation annotation, Field field) {
        return delegate.createMockFor(annotation, field);
    }
    
    /**
     * Process the fields of the test instance and create Mocks, Spies, Captors and inject them on
     * fields
     * annotated &#64;InjectMocks.
     * 
     * <p>
     * This code process the test class and the super classes.
     * <ol>
     * <li>First create Mocks, Spies, Captors.</li>
     * <li>Then try to inject them.</li>
     * </ol>
     * 
     * @param clazz Not used
     * @param testInstance The instance of the test, should not be null.
     * 
     * @see org.mockito.configuration.AnnotationEngine#process(Class, Object)
     */
    @Override
    public void process(Class<?> clazz, Object testInstance) {
        delegate.process(clazz, testInstance);
        
        // inject instances into testInstance
        processIndependentAnnotations(testInstance.getClass(), testInstance);
        // inject dependencies
        processInjection(testInstance.getClass(), testInstance);
    }
    
    private void processIndependentAnnotations(final Class<?> clazz, final Object testInstance) {
        Class<?> classContext = clazz;
        while (classContext != Object.class) {
            processInstances(classContext, testInstance);
            
            classContext = classContext.getSuperclass();
        }
    }
    
    private void processInstances(Class<?> classContext, Object testInstance) {
        // inject instances into testInstance
        Field[] fields = classContext.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object instance = createInstanceFor(field, testInstance);
                if (instance != null) {
                    try {
                        new FieldSetter(testInstance, field).set(instance);
                    } catch (Exception e) {
                        throw new MockitoException("Problems setting field " + field.getName() + " annotated with "
                                + Inject.class, e);
                    }
                }
            }
        }
    }
    
    private Object createInstanceFor(final Field needingInjection, final Object testInstance) {
        try {
            FieldInitializationReport report = new FieldInitializer(testInstance, needingInjection).initialize();
            Object instance = report.fieldInstance();
            return instance;
        } catch (MockitoException e) {
            throw new MockitoException(join("Cannot instantiate a @Inject for '" + needingInjection.getName() + "' field.",
                    "You haven't provided the instance at field declaration so I tried to construct the instance.",
                    "However, I failed because: " + e.getMessage(),
                    "Examples of correct usage of @Inject:",
                    "   @Inject List mock = new LinkedList();",
                    "   @Inject Foo foo; //only if Foo has parameterless constructor",
                    "   //also, don't forget about MockitoAnnotations.initMocks();",
                    ""), e);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void assertNoAnnotations(final Field field, final Class... annotations) {
        for (Class annotation : annotations) {
            if (field.isAnnotationPresent(annotation)) {
                new Reporter().unsupportedCombinationOfAnnotations(annotation.getSimpleName(), Inject.class.getSimpleName());
            }
        }
    }
    
    /**
     * Initializes instances/mock/spies/entity managers dependencies for objects annotated with
     * &#064;Inject for given testClass.
     * <p>
     * See examples in javadoc for {@link MockitoAnnotations} class.
     * 
     * @param testInstance
     * Test class, usually <code>this</code>
     */
    private void processInjection(Class<?> clazz, Object testInstance) {
        Set<Field> mockDependentFields = new HashSet<Field>();
        Set<Object> mocks = new HashSet<Object>();
        
        while (clazz != Object.class) {
            mockDependentFields.addAll(scanForInjection(testInstance, clazz));
            mocks.addAll(scanMocks(testInstance, clazz));
            clazz = clazz.getSuperclass();
        }
        
        MockInjection.onFields(mockDependentFields, testInstance)
                .withMocks(mocks)
                .tryPropertyOrFieldInjection()
                .apply();
    }
    
    /**
     * Scan fields annotated by &#064;Inject
     * 
     * @param testClass
     * @param clazz
     * @return
     */
    @SuppressWarnings("deprecation")
    private Set<Field> scanForInjection(final Object testClass, final Class<?> clazz) {
        Set<Field> mockDependentFields = new HashSet<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (null != field.getAnnotation(Inject.class)) {
                assertNoAnnotations(field, InjectMocks.class, Mock.class, MockitoAnnotations.Mock.class, Captor.class);
                mockDependentFields.add(field);
            }
        }
        
        return mockDependentFields;
    }
    
    @SuppressWarnings("deprecation")
    private Set<Object> scanMocks(final Object testClass, final Class<?> clazz) {
        Set<Object> mocks = new HashSet<Object>();
        for (Field field : clazz.getDeclaredFields()) {
            // instances, mock and spies
            if (null != field.getAnnotation(Inject.class)
                    || null != field.getAnnotation(Spy.class) || null != field.getAnnotation(org.mockito.Mock.class)
                    || null != field.getAnnotation(org.mockito.MockitoAnnotations.Mock.class)) {
                Object fieldInstance = get(testClass, field);
                boolean wasAccessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    fieldInstance = field.get(testClass);
                } catch (IllegalAccessException e) {
                    throw new MockitoException("Problems reading this field dependency " + field.getName() + " for injection", e);
                } finally {
                    field.setAccessible(wasAccessible);
                }
                if (fieldInstance != null) {
                    mocks.add(fieldInstance);
                }
            } else if (null != field.getAnnotation(Rule.class)
                    && field.getType() == TestingPersistenceUnit.class) {
                Object testingPersistenceUnit = get(testClass, field);
                if (testingPersistenceUnit != null) {
                    Object entityManager;
                    try {
                        entityManager = get(testingPersistenceUnit, testingPersistenceUnit.getClass().getDeclaredField("injectedEntityManager"));
                        if (entityManager == null) {
                            throw new MockitoException(join("Field @Rule " + field.getType() + " is not initialised properly.",
                                    "There is no EntityManager instance for injection.",
                                    "Please check that you are using JUnit > 4.10 and implementation of Runer is conform with JUnit 4.10 specyfication (use custome @Rules"));
                        }
                        mocks.add(entityManager);
                    } catch (SecurityException e) {
                        throw new MockitoException("Trying to get TestingPersistenceUnit.injectedEntityManager property", e);
                    } catch (NoSuchFieldException e) {
                        throw new MockitoException("Property TestingPersistenceUnit.injectedEntityManager renamed or removed please review ExtendedAnnotationEngine");
                    }
                }
            }
        }
        return mocks;
    }
    
    Object get(final Object instance, Field field) {
        Object fieldInstance;
        boolean wasAccessible = field.isAccessible();
        field.setAccessible(true);
        try {
            fieldInstance = field.get(instance);
        } catch (IllegalAccessException e) {
            throw new MockitoException("Problems reading this field " + field.getName(), e);
        } finally {
            field.setAccessible(wasAccessible);
        }
        return fieldInstance;
    }
}
