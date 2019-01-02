package com.xhh.concurrency.basic;

/**
 * 第二种创建线程的方式
 * 实现Runnable接口去单独定义线程任务。
 * @author adminitartor
 *
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        Runnable run1 = new MyRunnable1();
        Runnable run2 = new MyRunnable2();

        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run2);

        t1.start();
        t2.start();
    }
}
     class MyRunnable1 implements Runnable {
         @Override
         public void run() {
             for (int i = 0; i < 1000; i++) {
                 System.out.println("你是谁啊？");
             }
         }
     }

     class MyRunnable2 implements Runnable{
         @Override
         public void run() {
             for (int i = 0; i < 1000; i++) {
                 System.out.println("我是查水表的！");
             }
         }
     }