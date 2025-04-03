package com.bevan.thread;

import java.util.concurrent.Semaphore;

public class ThreadPrint {
    // static volatile Integer count = 0;
    // public static void main(String[] args) {
    //     Thread t1 = new Thread(() -> {
    //         while (count < 100) {
    //             count++;
    //             System.out.println(Thread.currentThread().getName() + " : " + count);
    //
    //         }
    //     });
    //
    //     Thread t2 = new Thread(() -> {
    //         while (count < 100) {
    //             count++;
    //             System.out.println(Thread.currentThread().getName() + " : " + count);
    //         }
    //     });
    //
    //     t1.start();
    //     t2.start();
    // }

    // static Semaphore s1 = new Semaphore(0);
    // static Semaphore s2 = new Semaphore(0);
    // static int num = 0;
    // public static void main(String[] args) {
    //     Thread t1 = new Thread(() -> {
    //         try {
    //             while (num < 100) {
    //                 num++;
    //                 System.out.println(Thread.currentThread().getName() + " " + num);
    //                 s2.release();
    //                 s1.acquire();
    //             }
    //         } catch (InterruptedException e) {
    //             throw new RuntimeException(e);
    //         }
    //     });
    //     Thread t2 = new Thread(() -> {
    //         try {
    //             while (num < 100) {
    //                 num++;
    //                 System.out.println(Thread.currentThread().getName() + " " + num);
    //                 s1.release();
    //                 s2.acquire();
    //             }
    //         } catch (InterruptedException e) {
    //             throw new RuntimeException(e);
    //         }
    //     });
    //
    //
    //     t1.start();
    //     t2.start();
    // }


    static class Counter {
        private int count = 0;
        private boolean isThread1Turn = true; // 控制交替的标志

        public synchronized void incrementAndPrint(boolean isThread1) {
            while (count <= 100) {
                // 检查是否轮到当前线程执行
                if (isThread1 != isThread1Turn) {
                    try {
                        wait(); // 未轮到则等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                // 执行累加和输出
                System.out.println(Thread.currentThread().getName() + ": " + count);
                count++;
                isThread1Turn = !isThread1Turn; // 切换执行权
                notifyAll(); // 唤醒另一个线程
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> counter.incrementAndPrint(true), "Thread-1");
        Thread thread2 = new Thread(() -> counter.incrementAndPrint(false), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // static Semaphore s1 = new Semaphore(0);
    // static Semaphore s2 = new Semaphore(0);
    // static Semaphore s3 = new Semaphore(0);
    //
    // public static void main(String[] args) {
    //     Thread t1 = new Thread(() ->  {
    //         for (int i = 0; i < 10; i++) {
    //             try {
    //                 System.out.println(1);
    //                 s1.acquire();
    //                 s2.release();
    //             } catch (InterruptedException e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }
    //     });
    //     Thread t2 = new Thread(() ->  {
    //         for (int i = 0; i < 10; i++) {
    //             try {
    //                 s2.acquire();
    //                 System.out.println(1);
    //                 s3.release();
    //             } catch (InterruptedException e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }
    //     });
    //     Thread t3 = new Thread(() ->  {
    //         for (int i = 0; i < 10; i++) {
    //             try {
    //                 s3.acquire();
    //                 System.out.println(3);
    //                 s1.release();
    //             } catch (InterruptedException e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }
    //     });
    //
    //     t1.start();
    //     t2.start();
    //     t3.start();
    // }
}
