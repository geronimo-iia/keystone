package org.intelligentsia.keystone.api.kernel.study;


/**
 * <p>
 * An external server—also known as a personality— is a component that uses the microkernel for implementing its own view of the underlying
 * application domain. As already mentioned, a view denotes a layer of abstraction built on top of the atomic services provided by the
 * microkernel. Different external servers implement different policies for specific application domains.
 * </p>
 * <p>
 * External servers expose their functionality by exporting interfaces in the same way as the microkernel itself does. Each of these
 * external servers runs in a separate process. It receives service requests from client applications using the communication facilities
 * provided by the microkernel, interprets these requests, executes the appropriate services and returns results to its clients. The
 * implementation of services relies on microkernel mechanisms, so external servers need to access the microkernel’s programming interfaces.
 * </p>
 * *
 * <p>
 * Responsibility<br />
 * Provides programming interfaces for its clients. <br />
 * </p>
 * <p>
 * Collaborators<br>
 * Microkernel
 * </p>
 * 
 * @author jguibert
 */
public class ExternalServer implements LifeCycle {
    private Microkernel kernel = null;
    
    public Microkernel getKernel() {
        return kernel;
    }
    
    public void setKernel(Microkernel kernel) {
        this.kernel = kernel;
    }
    
    public void receiveRequest(Object request, Object response) {
        dispatchRequest(request, response);
        service(request, response);
    }
    
    public void dispatchRequest(Object request, Object response) {
        
    }
    
    public void service(Object request, Object response) {
        
    }
    
    public void calls() {
        
    }
    
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
}
