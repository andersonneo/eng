package com.eng.framework.requestmon;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.eng.framework.log.Log;

public class Logger {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/HHmmss");
    private Logger() {
    }

    public static void println(String s) {
        Date date = new Date();
        Log.warn("TRACE", df.format(date) + ":" + s);
    }

}
