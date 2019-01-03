package com.xhh.concurrency.example.classic;

/**
 *  1) volatile修饰的变量并不保证对它的操作（自增）具有原子性。
 *    （对于自增操作，可以使用JAVA的原子类AutoicInteger类保证原子自增）
 *  2)比如，假设 i 自增到 5，线程A从主内存中读取i，值为5，将它存储到自己的线程空间中，执行加1操作，值为6。
 *    此时，CPU切换到线程B执行，从主从内存中读取变量i的值。
 *    由于线程A还没有来得及将加1后的结果写回到主内存，线程B就已经从主内存中读取了i，
 *    因此，线程B读到的变量 i 值还是5.相当于线程B读取的是已经过时的数据了，从而导致线程不安全性。
 *    这种情形在《Effective JAVA》中称之为“安全性失败”
 *   3) 综上，仅靠volatile不能保证线程的安全性。（原子性）
 */
public class volatileDemo2 {
    public static void main(String[] args) {
        MyThread[] mythreadArray = new MyThread[100];
        for (int i = 0; i < 100; i++) {
            mythreadArray[i] = new MyThread();
        }

        for (int i = 0; i < 100; i++) {
            mythreadArray[i].start();
        }
    }
}

class MyThread extends Thread {
    public volatile static int count;

    private static void addCount() {
        for (int i = 0; i < 100; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        addCount();
    }
}