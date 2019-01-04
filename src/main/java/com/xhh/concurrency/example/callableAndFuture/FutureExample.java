package com.xhh.concurrency.example.callableAndFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Callable和Future介绍:
 * 1) Callable接口代表一段可以调用并返回结果的代码;
 *    Future接口表示异步任务，是还没有完成的任务给出的未来结果。所以说Callable用于产生结果，Future用于获取结果。
 * 2) Callable接口使用泛型去定义它的返回类型。Executors类提供了一些有用的方法在线程池中执行Callable内的任务。
 *    由于Callable任务是并行的（并行就是整体看上去是并行的，其实在某个时间点只有一个线程在执行），我们必须等待它返回的结果。
 * 3) 在线程池提交Callable任务后返回了一个Future对象，使用它可以知道Callable任务的状态和得到Callable返回的执行结果。
 *    Future提供了get()方法让我们可以等待Callable结束并获取它的执行结果。
 */
@Slf4j
public class FutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        //Future接口:
        //isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
        //isDone方法表示任务是否已经完成，若任务完成，则返回true；
        //get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
        //get(long timeout, TimeUnit unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就返回TimeoutException。
        String result = future.get();
        //String result = future.get(1000,TimeUnit.MILLISECONDS);
        log.info("result: {}",result);
    }

    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }
}
