package main.java.com.bevan.thread.source;

import java.util.concurrent.*;

/**
 * @author zbf
 * @since 2024/6/11 下午6:27
 *
 * 并行数据处理流水线
 * 题目：编写一个多线程程序，模拟数据处理流水线。第一阶段读取数据，第二阶段处理数据，第三阶段保存数据。
 * 每个阶段由不同的线程池处理，并使用阻塞队列在各阶段之间传递数据。
 */
public class DataPipeline {
    private static final int QUEUE_CAPACITY = 10;
    private static final BlockingQueue<String> readQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<String> processQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final ExecutorService readExecutor = Executors.newSingleThreadExecutor();
    private static final ExecutorService processExecutor = Executors.newFixedThreadPool(2);
    private static final ExecutorService saveExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        readExecutor.execute(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    String data = "Data-" + i;
                    readQueue.put(data);
                    System.out.println("Read: " + data);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        processExecutor.execute(() -> {
            try {
                while (true) {
                    String data = readQueue.take();
                    String processedData = process(data);
                    processQueue.put(processedData);
                    System.out.println("Processed: " + processedData);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        saveExecutor.execute(() -> {
            try {
                while (true) {
                    String data = processQueue.take();
                    save(data);
                    System.out.println("Saved: " + data);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        readExecutor.shutdown();
        readExecutor.awaitTermination(1, TimeUnit.MINUTES);
        processExecutor.shutdown();
        processExecutor.awaitTermination(1, TimeUnit.MINUTES);
        saveExecutor.shutdown();
        saveExecutor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static String process(String data) {
        return "Processed-" + data;
    }

    private static void save(String data) {
        // Simulate saving data
    }
}
