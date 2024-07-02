package com.htjs.juc.chapter01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程优先级
 */
public class Priority {

    private static volatile  boolean notStart = true;
    private static volatile  boolean notEnd = true;

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<Job>();
        for(int i = 0; i < 10; i++) {
            int priotity = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priotity);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priotity);
            thread.start();
        }
        notStart = false;
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notEnd = false;

        for(Job job : jobs) {
            System.out.println("Job Priority:" + job.priority + ", Count:" + job.jobCount);
            /**
             * Job Priority:1, Count:0
             * Job Priority:1, Count:0
             * Job Priority:1, Count:0
             * Job Priority:1, Count:0
             * Job Priority:1, Count:0
             * Job Priority:10, Count:1058004
             * Job Priority:10, Count:1058798
             * Job Priority:10, Count:1059413
             * Job Priority:10, Count:1058796
             * Job Priority:10, Count:1060890
             * 优先级相同的计算结果相近，优先级低的压根没有被执行，说明 Win 10 JDK1.8 修改线程优先级起作用，其他操作系统待尝试
             */
        }
    }

    static class Job implements Runnable {
        private int priority;
        private int jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while(notStart) {
                Thread.yield();
            }
            while(notEnd) {
                //让出CPU资源
                Thread.yield();
                jobCount++;
            }
        }
    }

}
