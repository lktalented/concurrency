package com.xhh.concurrency.example.classic;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架是Java7提供了的一个用于并行执行任务的框架， 是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。
 *
 *  工作窃取（work-stealing）算法是指某个线程从其他队列里窃取任务来执行:
 *  假如我们需要做一个比较大的任务，我们可以把这个任务分割为若干互不依赖的子任务，为了减少线程间的竞争，
 *  于是把这些子任务分别放到不同的队列里，并为每个队列创建一个单独的线程来执行队列里的任务，线程和队列一一对应，
 *  比如A线程负责处理A队列里的任务。但是有的线程会先把自己队列里的任务干完，而其他线程对应的队列里还有任务等待处理。
 *  干完活的线程与其等着，不如去帮其他线程干活，于是它就去其他线程的队列里窃取一个任务来执行。
 *  而在这时它们会访问同一个队列，所以为了减少窃取任务线程和被窃取任务线程之间的竞争，通常会使用双端队列，
 *  被窃取任务线程永远从双端队列的头部拿任务执行，而窃取任务的线程永远从双端队列的尾部拿任务执行。
 *
 *  工作窃取算法的优点是充分利用线程进行并行计算，并减少了线程间的竞争，
 *  其缺点是在某些情况下还是存在竞争，比如双端队列里只有一个任务时。
 *  并且消耗了更多的系统资源，比如创建多个线程和多个双端队列
 * luokai
 * 2019/1/4 0004 下午 5:41
 */

public class ForkJoinDemo extends RecursiveTask<Integer> {
    private static final long serialVersionUID = -3611254198265061729L;

    public static final int threshold = 20;
    private int start;
    private int end;

    public ForkJoinDemo(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if(canCompute) {
            for (int i=start; i<=end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值:" + i);
                sum += i;
            }
        }
        else {
            System.err.println("=====任务分解======");
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end)/2;
            ForkJoinDemo leftTask = new ForkJoinDemo(start, middle);
            ForkJoinDemo rightTask = new ForkJoinDemo(middle+1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkjoinPool = new ForkJoinPool();
        //生成一个计算任务，计算1+2+3+4
        ForkJoinDemo task = new ForkJoinDemo(1, 100);
        //执行一个任务
        Future<Integer> result = forkjoinPool.submit(task);
        try
        {
            System.out.println(result.get());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}