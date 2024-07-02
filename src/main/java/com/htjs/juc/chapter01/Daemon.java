package com.htjs.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * daemon线程
 * 主线程终止的时候，Daemon线程立即终止，但是daemon线程中的finally块没有执行
 * 因此在构建Daemon线程时，不能依靠finally 块中的内容来确保执行关闭或清理资源的逻辑
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
