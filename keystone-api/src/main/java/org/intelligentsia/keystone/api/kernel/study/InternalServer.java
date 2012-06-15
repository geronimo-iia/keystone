package org.intelligentsia.keystone.api.kernel.study;

/**
 * <p>
 * An internal server—also known as a subsystem extends the functionality provided by the microkernel. It represents a separate component
 * that offers additional functionality. The microkernel invokes the functionality of internal servers via service requests. Internal
 * servers can therefore encapsulate some dependencies on the underlying hardware or software system. For example, device drivers that
 * support speciﬁc graphics cards are good candidates for internal servers.
 * </p>
 * <p>
 * One of the design goals should be to keep the microkernel as small as possible to reduce memory requirements. Another goal is to provide
 * mechanisms that execute quickly, to reduce service execution time. Additional and more complex services are therefore implemented by
 * internal servers that the microkernel activates or loads only when necessary. You can consider internal servers as extensions of the
 * microkernel. Note that internal servers are only accessible by the microkernel component.
 * </p>
 * <p>
 * Responsibility<br />
 * Implements additional services<br />
 * Encapsulates some system specifics.<br />
 * </p>
 * <p>
 * Collaborators<br />
 * Microkernel
 * </p>
 * 
 * @author jguibert
 */
public class InternalServer implements LifeCycle {
    private Microkernel kernel = null;
    private boolean unavailable;
    
    public Microkernel getKernel() {
        return kernel;
    }
    
    public void setKernel(Microkernel kernel) {
        this.kernel = kernel;
    }
    
    public void receiveRequest() {
        
    }
    
    public void executeService() {
        
    }
    
    public void activate() {
        
    }
    
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
    
    public boolean isUnavailable() {
        return unavailable;
    }
    
}
