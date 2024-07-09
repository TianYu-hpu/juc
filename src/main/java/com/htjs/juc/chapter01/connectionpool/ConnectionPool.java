package com.htjs.juc.chapter01.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {
        if(initialSize > 0) {
            for(int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if(connection != null) {
            synchronized (pool) {
                //连接池释放后需要进行通知，这样其他消费者就能感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 在指定时间内无法获取连接，将返回null
     * @param millis
     * @return
     */
    public Connection fetchConnection(long millis) throws InterruptedException {
        synchronized (pool) {
            //完全超时
            if(millis <= 0) {
                while(pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + millis;
                long remaining = millis;
                while(pool.isEmpty() && remaining > 0) {
                    pool.wait(millis);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }

        }
    }
}
