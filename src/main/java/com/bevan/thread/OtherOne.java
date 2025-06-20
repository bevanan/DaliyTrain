package com.bevan.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class OtherOne {

    public static void main(String[] args) {
        // 修改：s1 初始许可为 1
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        Semaphore s3 = new Semaphore(0);

        AtomicInteger count = new AtomicInteger();

        Thread T1 = new Thread(() -> {
            while (count.get() < 100) {
                try {
                    s1.acquire();  // 初始 s1=1，第一次可获取
                    System.out.println("t1：" + count.incrementAndGet());
                    s2.release();  // 释放 s2 给 T2
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread T2 = new Thread(() -> {
            while (count.get() < 100) {
                try {
                    s2.acquire();  // 等待 T1 释放 s2
                    System.out.println("t2：" + count.incrementAndGet());
                    s3.release();  // 释放 s3 给 T3
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread T3 = new Thread(() -> {
            while (count.get() < 100) {
                try {
                    s3.acquire();  // 等待 T2 释放 s3
                    System.out.println("t3：" + count.incrementAndGet());
                    s1.release();  // 释放 s1 给 T1，继续循环
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        T1.start();
        T2.start();
        T3.start();
    }
}
