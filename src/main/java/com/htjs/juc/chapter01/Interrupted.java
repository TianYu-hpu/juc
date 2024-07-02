package com.htjs.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 中断
 */
public class Interrupted {

    public static void main(String[] args) {
        //sleep线程不停地尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        //busy线程不停地运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //休眠5s,让两个线程得到充分的运行
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sleepThread.interrupt();
        busyThread.interrupt();
        //休眠1s确保主线程能看到结果
        System.out.println("SleepThread interrupted is :" + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is :" + busyThread.isInterrupted());
        /**
         * SleepThread interrupted is :false
         * BusyThread interrupted is :true
         * java.lang.InterruptedException: sleep interrupted
         *
         * 	at java.lang.Thread.sleep(Native Method)
         * 	at java.lang.Thread.sleep(Thread.java:340)
         * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
         * 	at com.htjs.juc.chapter01.Interrupted$SleepRunner.run(Interrupted.java:43)
         * 	at java.lang.Thread.run(Thread.java:748)
         */
        //主线程休眠2s,防止sleepThread和busyThread立即退出
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sleepThread.interrupt();
        busyThread.interrupt();
        //休眠1s确保主线程能看到结果
        System.out.println("SleepThread interrupted is :" + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is :" + busyThread.isInterrupted());
        /**
         * SleepThread interrupted is :false
         * java.lang.InterruptedException: sleep interrupted
         * 	at java.lang.Thread.sleep(Native Method)
         *
         * BusyThread interrupted is :true
         *  at java.lang.Thread.sleep(Thread.java:340)
         * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
         * 	at com.htjs.juc.chapter01.Interrupted$SleepRunner.run(Interrupted.java:66)
         * 	at java.lang.Thread.run(Thread.java:748)
         */

        //主线程休眠2s,防止sleepThread和busyThread立即退出
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while(true) {

            }
        }
    }
}
