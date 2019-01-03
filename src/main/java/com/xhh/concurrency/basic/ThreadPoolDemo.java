package com.xhh.concurrency.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// a. 每次new Thread新建对象性能差。
// b. 线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机或oom。
// c. 缺乏更多功能，如定时执行、定期执行、线程中断。
// 相比new Thread，Java提供的四种线程池的好处在于：
// a. 重用存在的线程，减少对象创建、消亡的开销，性能佳。
// b. 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
// c. 提供定时执行、定期执行、单线程、并发数控制等功能。
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Runnable runn = new Runnable() {
                @Override
                public void run() {
                    Thread t = Thread.currentThread();
                    System.out.println(t.getName()+":正在执行任务");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(t.getName()+"执行任务完毕！");
                }
            };
            threadPool.execute(runn);
            System.out.println("将任务"+i+"指派给线程池");
        }

        //停止线程池(等待线程池中的线程执行完毕后，停止)
        threadPool.shutdown();
        //立即停止线程池(无论是否有线程被执行)
        //threadPool.shutdownNow();
        System.out.println("停止了线程池！");
    }
}
