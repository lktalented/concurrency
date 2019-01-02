package com.xhh.concurrency.basic;
/**
 * 使用匿名内部类来完成两种方式的线程创建
 * @author adminitartor
 *
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("你是谁啊？");
            }
        });
        Thread t2 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("我是查水表的！");
                }
            }
        };

        t1.start();
        t2.start();
    }
}
