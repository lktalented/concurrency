package com.xhh.concurrency.basic;
/**
 * 守护线程，又称为:后台线程
 * 使用上与前台线程并无区别，所有线程默认创建出来都是
 * 前台线程，想变为守护线程需要单独设置。
 *
 * 但是结束时机上有一点不同:
 * 当进程结束时，所有正在运行的守护线程都会被强制结束。
 * 而一个进程中所有前台线程都结束，进程就会结束。
 * @author adminitartor
 *
 */
public class Thread_setDaemon {
    public static void main(String[] args) {
        /*
         * rose:前台线程
         */
        Thread rose = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("rose:let me go!");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("rose:啊啊啊啊啊啊啊.....");
                System.out.println("音效:噗通！！！");
            }
        };

        Thread jack = new Thread(() -> {
            while (true){
                System.out.println("jack:you jump! I jump!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        rose.start();
        //设置守护线程必须在线程启动前进行
        jack.setDaemon(true);
        jack.start();

    }
}
