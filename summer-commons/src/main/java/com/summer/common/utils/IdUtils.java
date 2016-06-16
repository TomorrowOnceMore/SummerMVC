package com.summer.common.utils;

import java.util.Random;

/**
 * Created by toy on 6/11/16.
 */
public class IdUtils {
    public static long genItemId() {
        long millis = System.currentTimeMillis();

        Random random = new Random();
        int end2 = random.nextInt(99);
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
}
