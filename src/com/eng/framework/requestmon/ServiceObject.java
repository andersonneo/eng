package com.eng.framework.requestmon;

public class ServiceObject {

    public String uri;
    public int count;
    public long Elapsed;

    public ServiceObject() {
        uri = null;
        count = 0;
        Elapsed = 0L;
    }

    public int getCount() {
        return count;
    }

    public long getElapsed() {
        return Elapsed;
    }

    public String getURI() {
        return uri;
    }

    public void increaseDepth() {
        count++;
    }

    public void setCount(int i) {
        count = i;
    }

    public void setData(String s, int i, long l) {
        uri = s;
        count = i;
        Elapsed = l;
    }

    public void setElapsed(long l) {
        Elapsed += l;
    }

}
