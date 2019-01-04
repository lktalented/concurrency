package com.xhh.concurrency.basic;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 1) 每个Random实例里面有一个原子性的种子变量用来记录当前的种子的值，当要生成新的随机数时候要根据当前种子计算新的种子并更新回原子变量。
 *    多线程下使用单个Random实例生成随机数时候，多个线程同时计算随机数计算新的种子时候多个线程会竞争同一个原子变量的更新操作，
 *    由于原子变量的更新是CAS操作，同时只有一个线程会成功，所以会造成大量线程进行自旋重试，这是会降低并发性能的。
 * 2) ThreadLocal的出现就是为了解决多线程访问一个变量时候需要进行同步的问题，
 *    让每一个线程拷贝一份变量，每个线程对变量进行操作时候实际是操作自己本地内存里面的拷贝，从而避免了对共享变量进行同步。
 * 3) Random类也是线程安全的。只是由于对原子的CAS同步操作，导致低效。ThreadLocalRandom可解决低效的问题
 *
 * luokai
 * 2019/1/4 0004 上午 10:24
 */
public class ThreadLocalRandomDemo {
    public static void main(String[] args) {


        //(获取一个随机数生成器
        ThreadLocalRandom random =  ThreadLocalRandom.current();

        //Random random = new Random();

        //(输出10个在0-5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; ++i) {
            System.out.println(random.nextInt(5));
        }
    }
}
