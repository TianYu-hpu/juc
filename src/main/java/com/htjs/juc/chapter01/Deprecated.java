package com.htjs.juc.chapter01;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 线程标记过期方法 suspend,resume,stop
 * 以suspend方法为例，现成暂停后不会释放已经占有的资源(比如锁)
 * stop方法停止的时候不能保证线程的资源正确释放，会导致程序工作在不正常状态
 */
public class Deprecated {

    /**
     * PrintThreadRun at20:34:38
     * PrintThreadRun at20:34:39
     * PrintThreadRun at20:34:40
     * main suspend PrintThread at:20:34:41
     * PrintThreadRun at20:34:44
     * main resume PrintThread at:20:34:44
     * PrintThreadRun at20:34:45
     * PrintThreadRun at20:34:46
     * main stop PrintThread at:20:34:47
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread暂定，输出内容停止
        printThread.suspend();
        System.out.println("main suspend PrintThread at:" + dateFormat.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread恢复，输出内容继续
        printThread.resume();
        System.out.println("main resume PrintThread at:" + dateFormat.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread终止，输出内容停止
        printThread.stop();
        System.out.println("main stop PrintThread at:" + dateFormat.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
    }


    static class Runner implements Runnable {
        @Override
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            while(true) {
                System.out.println(Thread.currentThread().getName() + "Run at" + dateFormat.format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
