package com.xhh.concurrency.basic;

/**
 * 获取线程信息的相关方法
 * @author adminitartor
 *
 */
public class Thread_info {
    public static void main(String[] args) {
        //获取运行main方法的线程
        Thread main = Thread.currentThread();
        //获取线程ID
        long id = main.getId();
        System.out.println("id:" + id);
        //获取线程名
        String name = main.getName();
        System.out.println("name:" + name);
        //获取线程优先级
        int priority = main.getPriority();
        System.out.println("priority:"+ priority);
        //是否处于活动状态
        boolean isAlive = main.isAlive();
        System.out.println("isAlive:" + isAlive);
        //是否为守护线程
        boolean daemon = main.isDaemon();
        System.out.println("daemon:" + daemon);
        //是否是被中断的
        boolean isInterrupted = main.isInterrupted();
        System.out.println("isInterrupted:" + isInterrupted);
        //当前线程状态（NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED）
        Thread.State state = main.getState();
        System.out.println("state:" + state);
    }
}
