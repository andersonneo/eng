package com.eng.framework.requestmon;

import java.util.Enumeration;
import java.util.Hashtable;

public class DumpThread extends Thread {

    private Hashtable activelist;

    public DumpThread(Hashtable hashtable) {
        activelist = null;
        activelist = hashtable;
    }

    public void run() {
        long l = System.currentTimeMillis();
        Enumeration enumeration = ((Hashtable) activelist.clone()).elements();
        try {
            while (enumeration.hasMoreElements()) {
                try {
                    TraceObject traceobject = (TraceObject) enumeration.nextElement();
                    long l1 = l - traceobject.getStartTime();
                    Logger.println("DUMP:" + l1 + ":" + traceobject.getURI());
                } catch (Exception _ex) {}
            }
        } catch (Exception _ex) {}
    }

}
