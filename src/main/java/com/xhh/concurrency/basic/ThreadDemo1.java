package com.xhh.concurrency.basic;

/**
 * 线程
 * 线程用来并发执行多段代码，感官上是"同时执行"，实际上
 * 所有线程都是走走停停的，这种执行现象称为并发。
 *
 * 线程有两种常用的创建方式。
 * 方式一:
 * 继承Thread并重写run方法，run方法是用来定义当前线程
 * 要执行的任务代码。
 * @author adminitartor
 *
 */
public class ThreadDemo1 {
    public static void main(String[] args) {
        Thread t1 = new MyThrea1();
        Thread t2 = new MyThread2();
        /*
         * 启动线程要调用start方法而不是run方法。
         * 当start方法调用完毕后，run方法会很快的被
         * 自动执行。
         */
        t1.start();
        t2.start();
    }

}

/**
 * 继承线程重写run方法这样的做法有两个不足:
 * 1:由于java是单继承，这就导致继承了Thread就不能继承
 *   其他类。
 * 2:由于重写run方法将线程的任务定义在了线程当中，这就
 *   导致线程的重用性变得很差。线程与任务不应有必然的
 *   耦合关系。
 * @author adminitartor
 *
 */
class MyThrea1 extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("你是谁啊？");
        }
    }
}

class MyThread2 extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("我是查水表的！");
        }
    }
}
