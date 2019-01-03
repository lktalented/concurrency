package com.xhh.concurrency.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Condition是在java 1.5中才出现的，它用来替代传统的Object的wait()、notify()实现线程间的协作，
 * 相比使用Object的wait()、notify()，使用Condition的await()、signal()这种方式实现线程间协作更加安全和高效。
 * 因此通常来说比较推荐使用Condition，阻塞队列实际上是使用了Condition来模拟线程间协作。
 *
 * 1) Condition是个接口，基本的方法就是await()和signal()方法；
 * 2) Condition依赖于Lock接口，生成一个Condition的基本代码是lock.newCondition()
 * 3) 调用Condition的await()和signal()方法，都必须在lock保护之内，就是说必须在lock.lock()和lock.unlock之间才可以使用
 * 4) Conditon中的await()对应Object的wait()；
 * 5) Condition中的signal()对应Object的notify()；
 * 6) Condition中的signalAll()对应Object的notifyAll()。
 *
 * luokai
 * 2019/1/3 0003 下午 6:20
 */

public class ConditionDemo {

    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConditionDemo test = new ConditionDemo();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();

        consumer.start();
        producer.start();
    }

    class Consumer extends Thread{

        @Override
        public void run() {
            consume();
        }

        private void consume() {

            try {
                lock.lock();
                System.out.println("我在等一个新信号"+this.currentThread().getName());
                condition.await();

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                System.out.println("拿到一个信号"+this.currentThread().getName());
                lock.unlock();
            }

        }
    }

    class Producer extends Thread{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            try {
                lock.lock();
                System.out.println("我拿到锁"+this.currentThread().getName());
                condition.signalAll();
                System.out.println("我发出了一个信号："+this.currentThread().getName());
            } finally{
                lock.unlock();
            }
        }
    }

}
