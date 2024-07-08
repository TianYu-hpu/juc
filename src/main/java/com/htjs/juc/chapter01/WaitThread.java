package com.htjs.juc.chapter01;

import java.util.concurrent.TimeUnit;

public class WaitThread {

    private static Long result = 1L;

    public static void main(String[] args) throws InterruptedException {
        get(1000L);
    }


    private synchronized static Long get(long mills) throws InterruptedException {
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        //超时大于0并且result返回值不满足要求
        while((result == null) && remaining > 0) {
            TimeUnit.MILLISECONDS.sleep(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }

}
