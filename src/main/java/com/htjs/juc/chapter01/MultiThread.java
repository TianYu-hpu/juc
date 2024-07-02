package com.htjs.juc.chapter01;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 一个Java进程由多个线程组合而成
 */
public class MultiThread {

    public static void main(String[] args) {
        //获取Java线程管理的MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for(ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
            /**
             * [6]Monitor Ctrl-Break
             * [5]Attach Listener        Attach Listener线程是负责接收到外部的命令，而对该命令进行执行的并且吧结果返回给发送者。
             * 通常我们会用一些命令去要求jvm给我们一些反馈信息，如：java -version、jmap、jstack等等。如果该线程在jvm启动的时候
             * 没有初始化，那么，则会在用户第一次执行jvm命令时，得到启动。
             * [4]Signal Dispatcher     分发和管理 JVM 信号的线程
             * [3]Finalizer             调用对象 finalize 方法的线程
             * [2]Reference Handler     清除 Reference 的线程
             * [1]main                  main 线程,程序入口
             */
        }
    }

}
