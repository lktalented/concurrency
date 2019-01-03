package com.xhh.concurrency.basic;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 和 synchronized 优缺点
 * 1）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
 * 2）Lock和synchronized有一点非常大的不同，采用synchronized不需要用户去手动释放锁，
 *    当synchronized方法或者synchronized代码块执行完之后，系统会自动让线程释放对锁的占用；
 *    而Lock则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。
 * 3)通过Lock可以知道线程有没有成功获取到锁。synchronized无法办到。Lock提供了比synchronized更多的功能。
 *
 * luokai
 * 2019/1/3 0003 下午 5:18
 */
    public class LockDemo1 {
        private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    /**
     * lock()方法是平常使用得最多的一个方法，就是用来获取锁。如果锁已被其他线程获取，则进行等待。
     */
        Lock lock = new ReentrantLock();    //注意这个地方

        public static void main(String[] args)  {
            final LockDemo1 test = new LockDemo1();
            new Thread(){
                public void run() {
                    test.insert(Thread.currentThread());
                };
            }.start();
            new Thread(){
                public void run() {
                    test.insert(Thread.currentThread());
                };
            }.start();
        }

        public void insert(Thread thread) {
            lock.lock();
            try {
                System.out.println(thread.getName()+"得到了锁");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(thread.getName()+"释放了锁");
                lock.unlock();
            }
        }
    }

