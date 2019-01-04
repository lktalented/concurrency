package com.xhh.concurrency.basic;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1) ReadWriteLock同Lock一样也是一个接口，提供了readLock和writeLock两种锁的操作机制，一个是只读的锁，一个是写锁。
 * 2) 读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的(排他的)。 每次只能有一个写线程，但是可以有多个线程并发地读数据。
 * 3) 所有读写锁的实现必须确保写操作对读操作的内存影响。换句话说，一个获得了读锁的线程必须能看到前一个释放的写锁所更新的内容。
 * 4) 理论上，读写锁比互斥锁允许对于共享数据更大程度的并发。与互斥锁相比，读写锁是否能够提高性能取决于读写数据的频率、读取和写入操作的持续时间、以及读线程和写线程之间的竞争。

 * luokai
 * 2019/1/4 0004 上午 10:56
 */

public class ReadWriteLockDemo {

    public static void main(String[] args){
        ReadWriteLockTest rwd = new ReadWriteLockTest();
        //启动100个读线程
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rwd.get();
                }
            }).start();
        }
        //写线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                rwd.set((int)(Math.random()*101));
            }
        },"Write").start();
    }

      static class ReadWriteLockTest{
        //模拟共享资源--Number
        private int number = 0;
        // 实际实现类--ReentrantReadWriteLock，默认非公平模式
        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        //读
        public void get(){
            //使用读锁
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+" : "+number);
            }finally {
                readWriteLock.readLock().unlock();
            }
        }
        //写
        public void set(int number){
            readWriteLock.writeLock().lock();
            try {
                this.number = number;
                System.out.println(Thread.currentThread().getName()+" : "+number);
            }finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

}



