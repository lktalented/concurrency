package com.xhh.concurrency.example.commonUnsafe;

import com.xhh.concurrency.annotations.ThreadNotSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * 1) Vector
 *    Vector和ArrayList类似，是长度可变的数组，与ArrayList不同的是，Vector是线程安全的，它给几乎所有的public方法都加上了synchronized关键字。
 *    由于加锁导致性能降低，在不需要并发访问同一对象时，这种强制性的同步机制就显得多余，所以现在Vector已被弃用
 * 2) HashTable
 *    HashTable和HashMap类似，不同点是HashTable是线程安全的，它给几乎所有public方法都加上了synchronized关键字，
 *    还有一个不同点是HashTable的K，V都不能是null，但HashMap可以，它现在也因为性能原因被弃用了
 * 3) Collections包装方法
 *    Vector和HashTable被弃用后，它们被ArrayList和HashMap代替，但它们不是线程安全的，
 *    所以Collections工具类中提供了相应的包装方法把它们包装成线程安全的集合
 * 4) java.util.concurrent包中的集合
 *  a) ConcurrentHashMap:
 *     ConcurrentHashMap和HashTable都是线程安全的集合，它们的不同主要是加锁粒度上的不同。
 *     HashTable的加锁方法是给每个方法加上synchronized关键字，这样锁住的是整个Table对象。而ConcurrentHashMap是更细粒度的加锁
 *     在JDK1.8之前，ConcurrentHashMap加的是分段锁，也就是Segment锁，每个Segment含有整个table的一部分，这样不同分段之间的并发操作就互不影响
 *     JDK1.8对此做了进一步的改进，它取消了Segment字段，直接在table元素上加锁，实现对每一行进行加锁，进一步减小了并发冲突的概率
 *  b) CopyOnWriteArrayList和CopyOnWriteArraySet:
 *     它们是加了写锁的ArrayList和ArraySet，锁住的是整个对象，但读操作可以并发执行
 *  c) 除此之外还有ConcurrentSkipListMap、ConcurrentSkipListSet、ConcurrentLinkedQueue、ConcurrentLinkedDeque等，至于为什么没有ConcurrentArrayList，
 *     原因是无法设计一个通用的而且可以规避ArrayList的并发瓶颈的线程安全的集合类，只能锁住整个list，这用Collections里的包装类就能办到
 */
@Slf4j
@ThreadNotSafe
public class ArrayListExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // Semaphore可以控制某个共享资源可被同时访问的次数,即可以维护当前访问某一共享资源的线程个数,并提供了同步机制.
        // 例如控制某一个文件允许的并发访问的数量.
        final Semaphore semaphore = new Semaphore(threadTotal);
        // CountDownLatch是一个同步辅助类，它允许一个或多个线程一直等待直到其他线程执行完毕才开始执行。
        // 用给定的计数初始化CountDownLatch，其含义是要被等待执行完的线程个数。
        // 每次调用CountDown()，计数减1
        // 主程序执行到await()函数会阻塞等待线程的执行，直到计数为0
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int i) {
        list.add(i);
    }
}
