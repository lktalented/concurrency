package com.xhh.concurrency.example.callableAndFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 可以看到这个接口实现了Runnable和Future接口，接口中的具体实现由FutureTask来实现
 * FutureTask可以由执行者调度，这一点很关键。
 * 它对外提供的方法基本上就是Future和Runnable接口的组合：get()、cancel、isDone()、isCancelled()和run()，而run()方法通常都是由执行者调用，我们基本上不需要直接调用它
 */
@Slf4j
public class FutureTaskExample {
    public static void main(String[] args) throws Exception {
        FutureTask<String>futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();
        log.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info("result：{}", result);
    }
}
