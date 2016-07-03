package com.summer.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by toy on 7/3/16.
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }
}
