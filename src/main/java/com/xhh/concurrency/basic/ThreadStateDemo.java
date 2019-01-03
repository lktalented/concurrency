package com.xhh.concurrency.basic;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.New（新创建）
 * 当用new操作符创建一个新线程时，如new Thread(r)，该线程还没有开始运行，它的当前状态为new，在线程运行之前还有一些基础工作要做。
 *
 * 2.Runnable（可运行）（运行）
 * 一旦线程调用start方法，线程处于runnable状态。在这个状态下的线程可能正在运行也可能没有运行（也就是说Java规范中正在运行的线程仍然处于可运行状态）。
 * 在线程运行之后或者从阻塞、等待或睡眠状态回来后，也返回到可运行状态。
 *
 * 3.Blocked（被阻塞）
 * 这个状态下, 是在多个线程有同步操作的场景, 比如正在等待另一个线程的synchronized 块的执行释放,
 * 或者可重入的 synchronized块里别人调用wait() 方法, 也就是线程在等待进入临界区。
 *
 * 4.Waiting（等待）
 * 这个状态下是指线程拥有了某个锁之后, 调用了它的wait方法,或者线程调用了join方法 join了另外的线程， 等待被他join的线程执行结束的状态。
 * 这里要区分阻塞状态 和等待状态的区别, 一个是在临界点外面等待进入, 一个是在临界点里面wait。
 *
 * 5.Timed waiting（计时等待）
 * 当线程调用带有超时参数的方法时会导致线程进入计时等待状态，
 * 带有超市参数的方法有Thread.sleep、Object.wait、Thread.join、Lock.tryLock以及Condition.await的计时版。
 *
 * 6.Terminated（被终止）
 * 　1）线程因为run方法正常退出而自然死亡。
 * 　2）因为一个没有捕获的异常终止了run方法而意外死亡。
 *
 */
public class ThreadStateDemo {
    public static void main(String args[]) throws Exception{
        MyThread thrd = new MyThread();
        thrd.setName("MyThread #1");
        showThreadStatus(thrd);
        thrd.start();
        Thread.sleep(50);
        showThreadStatus(thrd);
        thrd.waiting = false;
        Thread.sleep(50);

        showThreadStatus(thrd);
        thrd.notice();
        Thread.sleep(50);
        showThreadStatus(thrd);
        while(thrd.isAlive())
            System.out.println("alive");
        showThreadStatus(thrd);
    }

    static void showThreadStatus(Thread thrd) {
        System.out.println(thrd.getName()+" 存活:" +thrd.isAlive()+" 状态:" + thrd.getState() );
    }}

    class MyThread extends Thread{
        boolean waiting= true;
        boolean ready= false;
        MyThread() {
        }

        public void run() {
            String thrdName = Thread.currentThread().getName();
            System.out.println(thrdName + " 启动");
            while(waiting){
                System.out.println("等待："+waiting);
            }
            System.out.println("等待...");
            startWait();
            try {
                Thread.sleep(200);
            }
            catch(Exception exc) {
                System.out.println(thrdName + " 中断。");
            }
            System.out.println(thrdName + " 结束。");

        }

        synchronized void startWait() {
            try {
//                1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
//                2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。
//                3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
//                当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
//                只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
//                也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程
//                4、wait() 需要被try catch包围，中断也可以使wait等待的线程唤醒。
//                5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
                while(!ready) wait();
            }
            catch(InterruptedException exc) {
                System.out.println("wait() 中断。");
            }

        }
        synchronized void notice() {
            ready = true;
            notify();
        }}