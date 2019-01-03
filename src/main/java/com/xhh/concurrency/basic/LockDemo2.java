package com.xhh.concurrency.basic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1)tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，
 *   如果获取失败（即锁已被其他线程获取），则返回false，也就说这个方法无论如何都会立即返回。在拿不到锁时不会一直在那等待。
 * 2)tryLock(long time, TimeUnit unit)方法和tryLock()方法是类似的，只不过区别在于这个方法在拿不到锁时会等待一定的时间，
 *   在时间期限之内如果还拿不到锁，就返回false。如果如果一开始拿到锁或者在等待期间内拿到了锁，则返回true
 *
 * luokai
 * 2019/1/3 0003 下午 5:34
 */
public class LockDemo2 {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();    //注意这个地方
    public static void main(String[] args)  {
        final LockDemo2 test = new LockDemo2();

        new Thread(){
            public void run() {
                try {
                    test.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread(){
            public void run() {
                try {
                    test.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public void insert(Thread thread) throws InterruptedException {
        //if(lock.tryLock(0, TimeUnit.MILLISECONDS)) {
        if(lock.tryLock()) {
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
        } else {
            System.out.println(thread.getName()+"获取锁失败");
        }
    }
}


