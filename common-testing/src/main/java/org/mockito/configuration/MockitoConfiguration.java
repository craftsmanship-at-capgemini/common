package org.mockito.configuration;

import org.mockito.internal.configuration.InjectingAnnotationEngine;

import testing.internal.ExtendedAnnotationEngine;


/**
 * Configure Mockito to using the {@link ExtendedAnnotationEngine} instead of default
 * {@link InjectingAnnotationEngine}.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration implements IMockitoConfiguration {
    /**
     * {@inheritDoc}
     * 
     * @see org.mockito.configuration.IMockitoConfiguration#getAnnotationEngine()
     */
    @Override
    public AnnotationEngine getAnnotationEngine() {
        return new ExtendedAnnotationEngine();
    }
}
