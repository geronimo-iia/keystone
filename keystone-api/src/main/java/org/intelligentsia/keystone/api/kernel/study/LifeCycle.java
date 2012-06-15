package org.intelligentsia.keystone.api.kernel.study;

/**
 * Common interface for component life cycle methods.
 * 
 * @author Jerome Guibert
 */
public interface LifeCycle {
    /**
     * Component instance initialization.
     */
    public void initialize();
    
    /**
     * Component instance disposal.
     */
    public void dispose();
}
