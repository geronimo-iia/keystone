package org.intelligentsia.keystone.api.kernel.study;

/**
 * A client is an application that is associated with exactly one external server. It only accesses the programming interfaces provided by
 * the external server.
 * 
 * @author jguibert
 */
public class Client {
    private Adapter adapter = null;
    
    public Adapter getAdapter() {
        return adapter;
    }
    
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }
    
    public void doTask() {
        adapter.callService();
    }
}
