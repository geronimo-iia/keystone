package org.intelligentsia.keystone.api.kernel.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CallContext {
    protected ExecutorService executor;
    
    public void test() {
        Future<Object> future = executor.submit(new Callable<Object>() {

            public Object call() throws Exception {
                return null;
            }
            
        }) ;
        
        try {
            Object object = future.get();
            if (object!=null) {
                object.toString();
            }
            
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }
}
