package com.xhh.concurrency.basic;
/**
 * 线程优先级
 *
 * 线程对线程调度的工作是不可控的，即:
 * 线程只能被动被分配CPU时间，而不能主动获取到。
 *
 * 线程调度会尽可能的将CPU时间分配的几率做到均匀。但是
 * 多个线程并发运行，并不保证一个线程一次这样交替运行。
 *
 * 可以通过调整线程的优先级改善分配CPU时间片几率。
 * 理论上线程优先级越高的线程，获取CPU时间片的次数就多。
 *
 * 线程的优先级有10级，分别用整数1-10表示
 * 其中1最低，5默认，10最高
 *
 * @author adminitartor
 *
 */
public class Thread_setPriority {
    public static void main(String[] args) {
            Thread max = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    System.out.println("max");
                }
            });
            Thread nor = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println("nor");
                    }
                }
            };
            Thread min = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println("min");
                    }
                }
            };

            max.setPriority(Thread.MAX_PRIORITY);
            min.setPriority(Thread.MIN_PRIORITY);

            min.start();
            nor.start();
            max.start();
            
    }
}
