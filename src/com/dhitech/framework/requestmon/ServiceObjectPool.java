package com.dhitech.framework.requestmon;

import java.util.Stack;

public class ServiceObjectPool {
    private static ServiceObjectPool instance = null;
    private Stack pool;
    public ServiceObjectPool() {
        pool = new Stack();
    }

    public void freeObject(ServiceObject serviceobject) {
        pool.push(serviceobject);
    }

    public static synchronized ServiceObjectPool getInstance() {
        if (instance == null) {
            instance = new ServiceObjectPool();
        }
        return instance;
    }

    public synchronized ServiceObject getObject() {
        ServiceObject serviceobject = null;
        if (pool.empty()) {
            serviceobject = new ServiceObject();
        } else {
            serviceobject = (ServiceObject) pool.pop();
        }
        return serviceobject;
    }

}
