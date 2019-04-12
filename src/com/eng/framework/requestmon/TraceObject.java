package com.eng.framework.requestmon;

public class TraceObject {

    public String uri;
    public long start;
    public String address;
    public int depth;

    public TraceObject() {
        uri = null;
        start = 0L;
        address = null;
        depth = 0;
        uri = null;
        start = 0L;
        address = null;
        depth = 0;
    }

    public int getDepth() {
        return depth;
    }

    public String getRemoteAddr() {
        return address;
    }

    public long getStartTime() {
        return start;
    }

    public String getURI() {
        return uri;
    }

    public void increaseDepth() {
        depth++;
    }

    public void setData(String s, String s1, long l, int i) {
        uri = s;
        address = s1;
        start = l;
        depth = i;
    }

    public void setDepth(int i) {
        depth = i;
    }

}
