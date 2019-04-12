package com.eng.framework.requestmon;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class ServiceTrace {
    private static ServiceTrace instance = null;
    private Hashtable activelist;
    private Hashtable servicelist;
    private static int servicecount;
    private static int servicecounti;
    private long DELAY_TIME;
    private int DUMP_TRIGGER;
    private long dumped_time;

    private ServiceTrace() {
        DUMP_TRIGGER = 90;
        dumped_time = 0L;
        activelist = new Hashtable();
        servicelist = new Hashtable();
        servicecount = 0;
        servicecounti = 0;
        DELAY_TIME = 3000L;
        try {
            DELAY_TIME = Long.parseLong(System.getProperty("trace.delaytime", "3000"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        DUMP_TRIGGER = 90;
        try {
            DUMP_TRIGGER = Integer.parseInt(System.getProperty("trace.dumptrigger", "90"));
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    public void clearservicecounti() {
        servicecounti = 0;
    }

    public void endTrace(HttpServletRequest httpservletrequest) {
        TraceObject traceobject = (TraceObject) activelist.get(httpservletrequest);
        if (traceobject == null) {
            return;
        }
        int i = traceobject.getDepth();
        if (i > 0) {
            traceobject.setDepth(--i);
            return;
        }
        activelist.remove(httpservletrequest);
        try {
            long l1 = System.currentTimeMillis() - traceobject.getStartTime();
            ServiceObject serviceobject = (ServiceObject) servicelist.get(httpservletrequest.getParameter("cmd"));
            if (serviceobject == null) {
                ServiceObjectPool serviceobjectpool = ServiceObjectPool.getInstance();
                serviceobject = serviceobjectpool.getObject();
                serviceobject.setData(httpservletrequest.getParameter("cmd"), 1, 0L);
                servicelist.put(httpservletrequest.getParameter("cmd"), serviceobject);
                servicecount++;
                servicecounti++;
            } else {
                serviceobject.increaseDepth();
                serviceobject.setElapsed(l1);
                servicelist.put(httpservletrequest.getParameter("cmd"), serviceobject);
                servicecount++;
                servicecounti++;
            }
            if (l1 > DELAY_TIME) {
                Logger.println("WARNING:" + l1 + ":" + httpservletrequest.getParameter("cmd") + "   remote url :" + httpservletrequest.getRemoteAddr());
            }
        } catch (Exception _ex) {}
        ObjectPool objectpool = ObjectPool.getInstance();
        objectpool.freeObject(traceobject);
    }

    public Hashtable getActiveList() {
        return activelist;
    }

    public static synchronized ServiceTrace getInstance() {
        if (instance == null) {
            instance = new ServiceTrace();
        }
        return instance;
    }

    public Hashtable getServiceList() {
        return servicelist;
    }

    public int getservicecount() {
        return servicecount;
    }

    public int getservicecounti() {
        return servicecounti;
    }

    public void startTrace(HttpServletRequest httpservletrequest) {
        TraceObject traceobject = (TraceObject) activelist.get(httpservletrequest);
        if (traceobject != null) {
            traceobject.increaseDepth();
            return;
        }
        ObjectPool objectpool = ObjectPool.getInstance();
        traceobject = objectpool.getObject();
        try {
            traceobject.setData(httpservletrequest.getParameter("cmd"), httpservletrequest.getRemoteAddr(), System.currentTimeMillis(), 0);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        activelist.put(httpservletrequest, traceobject);
        if (activelist.size() >= DUMP_TRIGGER) {
            long l = System.currentTimeMillis();
            if (l - dumped_time > 0x2bf20L) {
                dumped_time = l;
                DumpThread dumpthread = new DumpThread(activelist);
                dumpthread.start();
            }
        }
    }
}
