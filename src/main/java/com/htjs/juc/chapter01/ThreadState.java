package com.htjs.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 线程状态
 */
public class ThreadState {

    public static void main(String[] args) {
        new Thread(new TimeWating(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
        /**
         * "BlockedThread-2" #14 prio=5 os_prio=0 tid=0x000000002708d800 nid=0x5794 waiting for monitor entry [0x0000000027ecf000]
         *    java.lang.Thread.State: BLOCKED (on object monitor)
         *         at com.htjs.juc.chapter01.ThreadState$Blocked.run(ThreadState.java:56)
         *         - waiting to lock <0x0000000716197e08> (a java.lang.Class for com.htjs.juc.chapter01.ThreadState$Blocked)
         *         at java.lang.Thread.run(Thread.java:748)
         *
         * "BlockedThread-1" #13 prio=5 os_prio=0 tid=0x000000002708b000 nid=0x519c waiting on condition [0x0000000027dcf000]
         *    java.lang.Thread.State: TIMED_WAITING (sleeping)
         *         at java.lang.Thread.sleep(Native Method)
         *         at java.lang.Thread.sleep(Thread.java:340)
         *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
         *         at com.htjs.juc.chapter01.ThreadState$Blocked.run(ThreadState.java:56)
         *         - locked <0x0000000716197e08> (a java.lang.Class for com.htjs.juc.chapter01.ThreadState$Blocked)
         *         at java.lang.Thread.run(Thread.java:748)
         *
         * "WaitingThread" #12 prio=5 os_prio=0 tid=0x0000000027088000 nid=0x416c in Object.wait() [0x0000000027ccf000]
         *    java.lang.Thread.State: WAITING (on object monitor)
         *         at java.lang.Object.wait(Native Method)
         *         - waiting on <0x00000007161952c8> (a java.lang.Class for com.htjs.juc.chapter01.ThreadState$Waiting)
         *         at java.lang.Object.wait(Object.java:502)
         *         at com.htjs.juc.chapter01.ThreadState$Waiting.run(ThreadState.java:41)
         *         - locked <0x00000007161952c8> (a java.lang.Class for com.htjs.juc.chapter01.ThreadState$Waiting)
         *         at java.lang.Thread.run(Thread.java:748)
         *
         * "TimeWaitingThread" #11 prio=5 os_prio=0 tid=0x0000000027086000 nid=0x5840 waiting on condition [0x0000000027bce000]
         *    java.lang.Thread.State: TIMED_WAITING (sleeping)
         *         at java.lang.Thread.sleep(Native Method)
         *         at java.lang.Thread.sleep(Thread.java:340)
         *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
         *         at com.htjs.juc.chapter01.ThreadState$TimeWating.run(ThreadState.java:25)
         *         at java.lang.Thread.run(Thread.java:748)
         */
    }

    /**
     * 该线程不断进行睡眠
     */
    static class TimeWating implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //让线程在Waiting
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        //让出锁
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //让线程在Blocked.class上枷锁后，不会释放该锁
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
