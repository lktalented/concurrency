package com.xhh.concurrency.example.classic;

/**
 * 死锁概念：
 * 1) 当一个线程永远地持有一个锁，并且其他线程都尝试去获得这个锁时，那么它们将永远被阻塞。
 *    如果线程A持有锁L并且想获得锁M，线程B持有锁M并且想获得锁L，那么这两个线程将永远等待下去，这种情况就是最简单的死锁形式。
 * 2) 在数据库系统的设计中考虑了监测死锁以及从死锁中恢复，数据库如果监测到了一组事物发生了死锁时，将选择一个牺牲者并放弃这个事物。
 *    Java虚拟机解决死锁问题方面并没有数据库这么强大，当一组Java线程发生死锁时，这两个线程就永远不能再使用了，
 *    并且由于两个线程分别持有了两个锁，那么这两段同步代码/代码块也无法再运行了----除非终止并重启应用。
 * 3) 死锁造成的影响很少会立即显现出来，一个类可能发生死锁，并不意味着每次都会发生死锁，这只是表示有可能。
 *    当死锁出现时，往往是在最糟糕的情况----高负载的情况下。
 *
 * 避免死锁的方式*
 * 1、让程序每次至多只能获得一个锁。当然，在多线程环境下，这种情况通常并不现实
 * 2、设计时考虑清楚锁的顺序，尽量减少嵌在的加锁交互数量
 * 3、既然死锁的产生是两个线程无限等待对方持有的锁，那么只要等待时间有个上限不就好了。
 *    当然synchronized不具备这个功能，但是我们可以使用Lock类中的tryLock方法去尝试获取锁，
 *    这个方法可以指定一个超时时限，在等待超过该时限之后变回返回一个失败信息。
 *
 * luokai
 * 2019/1/4 0004 上午 9:30
 */

public class DeadLock {

    public static void main(String [] args) {
        DeadLock lock = new DeadLock() ;
        new ProxyLeftLock(lock).start() ;
        new ProxyRightLock(lock).start() ;
    }

    private final Object left  = new Object() ;
    private final Object right  = new Object() ;

    public void left() throws Exception {
        synchronized (left) {
            Thread.sleep(2000) ;
            synchronized (right) {
                System.out.println("左边") ;
            }
        }
    }

    public void right() throws Exception {
        synchronized (right) {
            Thread.sleep(2000) ;
            synchronized (left) {
                System.out.println("右边") ;
            }
        }
    }

     static class  ProxyLeftLock extends Thread {
        private DeadLock  lock ;
        public ProxyLeftLock(DeadLock lock) {
            this.lock = lock ;
        }
        @Override
        public void run() {
            try {
                lock.left() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }

    }

    static class ProxyRightLock extends Thread {
        private DeadLock lock ;
        public ProxyRightLock(DeadLock lock) {
            this.lock = lock ;
        }
        @Override
        public void run() {
            try {
                lock.right() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }

    }

}