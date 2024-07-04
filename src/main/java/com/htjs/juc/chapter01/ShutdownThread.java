package com.htjs.juc.chapter01;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 停止线程的两种方式
 */
public class ShutdownThread {


    /**
     * Count i:241581562
     * Count i:292393640
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread countThread = new Thread(new Runner(), "CountThread");
        countThread.start();
        //休眠1s,main线程对CountThread进行中断，使CountThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner onRunner = new Runner();
        Thread onThread = new Thread(onRunner, "OnThread");
        onThread.start();
        //休眠1s,main线程对onThread进行取消，使onThread能够感知到而结束
        TimeUnit.SECONDS.sleep(1);
        onRunner.cancel();
    }


    static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true;
        @Override
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            while(on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i:" + i);
        }

        public void cancel() {
            on = false;
        }
    }

}
