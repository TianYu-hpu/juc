package com.htjs.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 只有等前一个线程执行完后，后面的线程才能从join()方法返回
 */
public class Join {

    /**
     * main terminate
     * 0 terminate.
     * 1 terminate.
     * 2 terminate.
     * 3 terminate.
     * 4 terminate.
     * 5 terminate.
     * 6 terminate.
     * 7 terminate.
     * 8 terminate.
     * 9 terminate.
     * @param args
     */
    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous), "" + i);
            thread.start();
            previous = thread;
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " terminate");
    }


    static class Domino implements Runnable {

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

}
