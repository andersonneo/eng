package com.dhitech.framework.requestmon;

import java.util.Stack;

public class ObjectPool {

    public ObjectPool() {
        pool = new Stack();
    }

    public void freeObject(TraceObject traceobject) {
        pool.push(traceobject);
    }

    public static synchronized ObjectPool getInstance() {
        if (instance == null) {
            instance = new ObjectPool();
        }
        return instance;
    }

    public synchronized TraceObject getObject() {
        TraceObject traceobject = null;
        if (pool.empty()) {
            traceobject = new TraceObject();
        } else {
            traceobject = (TraceObject) pool.pop();
        }
        return traceobject;
    }

    private static ObjectPool instance = null;
    private Stack pool;

}
