package com.xhh.concurrency.example.classic;

/**
 * volatile关键字的作用是：使变量在多个线程间可见（可见性）
 * volatile关键字的非原子性
 * volatile关键字修饰的变量不会被指令重排序优化
 *
 *  volatile 与 synchronized 的比较
 * 1) volatile轻量级，只能修饰变量。synchronized重量级，还可修饰方法
 * 2) volatile只能保证数据的可见性，不能用来同步，因为多个线程并发访问volatile修饰的变量不会阻塞。
 * 3) synchronized不仅保证可见性，而且还保证原子性，
 *    因为，只有获得了锁的线程才能进入临界区，从而保证临界区中的所有语句都全部执行。
 *    多个线程争抢synchronized锁对象时，会出现阻塞。
 */
public class volatileDemo1 {
    public static void main(String[] args) {
        try {
            RunThread thread = new RunThread();
            thread.start();
            Thread.sleep(1000);
            thread.setRunning(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 class RunThread extends Thread {
    // 它强制线程从主内存中取 volatile修饰的变量。
     volatile  private boolean isRunning = true;

//     现在有两个线程，一个是main线程，另一个是RunThread。它们都试图修改 第三行的 isRunning变量。
//     按照JVM内存模型，main线程将isRunning读取到本地线程内存空间，修改后，再刷新回主内存。
//     而在JVM 设置成 -server模式运行程序时，线程会一直在私有堆栈中读取isRunning变量。
//     因此，RunThread线程无法读到main线程改变的isRunning变量
//     从而出现了死循环，导致RunThread无法终止。这种情形，在《Effective JAVA》中，将之称为“活性失败”

//    private boolean isRunning = true;
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入到run方法中了");
        while (isRunning == true) {
        }
        System.out.println("线程执行完成了");
    }
}
