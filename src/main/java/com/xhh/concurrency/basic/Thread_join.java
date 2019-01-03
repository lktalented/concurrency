package com.xhh.concurrency.basic;

/**
 * 线程是异步运行的
 * 异步:各执行各的，互相不妨碍
 * 同步:有先后顺序的执行。
 *
 * 有些业务需要让多个线程间同步运行，这时候可以借助
 * 线程的join方法来完成。
 *
 * join方法允许一个线程进入阻塞状态，直到其等待的另一个
 * 线程工作结束后再继续运行。
 * @author adminitartor
 *
 */
public class Thread_join {
    public static boolean isFinish = false;

    public static void main(String[] args) {
        final Thread download = new Thread(){
            @Override
            public void run() {
                System.out.println("down:开始下载图片...");
                for (int i = 0; i < 100; i++) {
                    System.out.println("down:"+i+"%");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("down:图片下载完毕！");
                isFinish = true;
            }
        };

        Thread show = new Thread(){
            @Override
            public void run() {
                System.out.println(" show: 开始显示图片...");
                try {
                    /**join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
                     程序在 show 线程中调用download 线程的join方法，则show线程放弃cpu控制权，并返回download 线程继续执行直到线程 ownload 执行完毕
                     所以结果是download 线程执行完后，才到show线程执行，相当于在show线程中同步download线程，download执行完了，show线程才有执行的机会
                     */
                    download.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isFinish){
                    throw new RuntimeException("show:图片加载失败！");
                }
                System.out.println("show：显示图片完毕！");
            }
        };

        download.start();
        show.start();
    }
}
