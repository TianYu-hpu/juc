package com.htjs.juc.chapter01.connectionpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);
    //保证所有connectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    //main线程将会等待所有ConnectionRunner结束后再继续运行
    static CountDownLatch end;


    /**
     * total invoke:2000
     * got connection:408
     * notGot connection:1592
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 20;
        end = new CountDownLatch(threadCount);
        int count = 100;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for(int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke:" + (threadCount * count));
        System.out.println("got connection:" + got);
        System.out.println("notGot connection:" + notGot);
    }


    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(count > 0) {
                try {
                    Connection connection = pool.fetchConnection(100);
                    if(connection != null) {
                        try{
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }

                } catch (Exception e) {

                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
