package org.intelligentsia.keystone.api.kernel.study;

public class Adapter {
    private ExternalServer server = null;
    private Microkernel kernel = null;
    
    public ExternalServer getServer() {
        return server;
    }
    
    public void setServer(ExternalServer server) {
        this.server = server;
    }
    
    public Microkernel getKernel() {
        return kernel;
    }
    
    public void setKernel(Microkernel kernel) {
        this.kernel = kernel;
    }
    
    /** called by client */
    public void callService() {
        createRequest();
        kernel.initCommunication(this);
        server.receiveRequest(null, null);
    }
    
    public void createRequest() {
        
    }
    
    public void sendRequest() {
        server.dispatchRequest(null, null);
    }
    
}
