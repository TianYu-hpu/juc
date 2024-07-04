package com.htjs.juc.chapter01;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    /**
     * WaitThreadflag is true wait at:          21:13:33
     * NotifyThreadhold lock, notify at:        21:13:38
     * NotifyThreadhold lock again at:          21:13:43
     * WaitThreadwait thread hold lock again at:21:13:48
     * @param args
     */
    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }




    static class Wait implements Runnable {
        @Override
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            synchronized (lock) {
                while(flag) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "flag is true wait at:" + dateFormat.format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "wait thread hold lock again at:" + dateFormat.format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "hold lock, notify at:" + dateFormat.format(new Date()));
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "hold lock again at:" + dateFormat.format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
