package com.xhh.concurrency.basic;

public class Thread_setPriority {
    public static void main(String[] args) {
            Thread max = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    System.out.println("max");
                }
            });
            Thread nor = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println("nor");
                    }
                }
            };
            Thread min = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println("min");
                    }
                }
            };

            max.setPriority(Thread.MAX_PRIORITY);
            min.setPriority(Thread.MIN_PRIORITY);

            min.start();
            nor.start();
            max.start();
            
    }
}
