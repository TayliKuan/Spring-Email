package com.example.demo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 處理 Exception 的公用程式
 */

public class ExceptionUtility {

    public static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
