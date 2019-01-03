package com.xhh.concurrency.basic;

/**
 * 互斥锁
 * 当synchronized修饰多段不同代码，但是锁对象相同时
 * 这些代码间具有互斥性，即:多个线程不能同时执行这些代码。
 * 在多线程程序中，多个线程可能会共用同一个对象，为了防止多个线程在争夺、使用同一份对象时可能会对该对象造成的改变，引入互斥锁。互斥锁可保证在任一时刻，只能有一个线程访问该对象，从而保证共享数据操作的完整性。
 *
 * @author adminitartor
 *
 */
public class SyncDemo4 {
    public static void main(String[] args) {
        final Boo boo = new Boo();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                boo.methodA();
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run() {
                boo.methodB();
            }
        };
        t1.start();
        t2.start();

    }
}

class Boo{
    public synchronized void methodA(){
        Thread t = Thread.currentThread();
        System.out.println(t.getName()+":正在执行A方法");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行A方法完毕");
    }
    public synchronized void methodB(){
        Thread t = Thread.currentThread();
        System.out.println(t.getName()+":正在执行B方法");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行B方法完毕");
    }



}