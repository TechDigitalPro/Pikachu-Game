package com.onet.onet.utils;

import android.util.Log;

public class ILogger
{
    public static String TAG_ERROR;
    public static String TAG_INFO;
    public static boolean islog;
    
    static {
        ILogger.islog = false;
        ILogger.TAG_INFO = "TAG_INFO";
        ILogger.TAG_ERROR = "TAG_ERROR";
    }
    
    public static void LogError(final String s) {
        if (ILogger.islog) {
            Log.e(ILogger.TAG_ERROR, s);
        }
    }
    
    public static void LogInfo(final String s) {
        if (ILogger.islog) {
            Log.i(ILogger.TAG_INFO, s);
        }
    }
}