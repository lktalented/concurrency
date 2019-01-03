package com.xhh.concurrency.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1) 当一个线程获取了锁之后，是不会被interrupt()方法中断的。
 *    因为本身在前面的文章中讲过单独调用interrupt()方法不能中断正在运行过程中的线程，只能中断阻塞过程中的线程。
 * 2) 因此当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
 * 3) 用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。
 * luokai
 * 2019/1/3 0003 下午 5:51
 */
public class LockDemo3 {
    private Lock lock = new ReentrantLock();
    public static void main(String[] args)  {
        LockDemo3 test = new LockDemo3();
        MyThread thread1 = new MyThread(test);
        MyThread thread2 = new MyThread(test);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }

    public void insert(Thread thread) throws InterruptedException{
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {
            System.out.println(thread.getName()+"得到了锁");
            long startTime = System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
        }
        finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }
    }

    static class MyThread extends Thread {
        private LockDemo3 test = null;

        public MyThread(LockDemo3 test) {
            this.test = test;
        }
        @Override
        public void run() {

            try {
                test.insert(Thread.currentThread());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"被中断");
            }
        }
    }
}

