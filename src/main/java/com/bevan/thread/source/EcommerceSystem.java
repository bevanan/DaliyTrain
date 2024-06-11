package main.java.com.bevan.thread.source;

import java.util.concurrent.*;

/**
 * @author zbf
 * @since 2024/6/11 下午4:54
 * <p>
 * 并发订单处理系统
 * 编写一个多线程程序，模拟电子商务系统中的并发订单处理。系统需要从队列中读取订单，检查库存，处理支付，并最终确认订单。
 * 每个步骤都由不同的线程池处理，使用阻塞队列在各步骤之间传递订单。
 */
public class EcommerceSystem {
    private static final int QUEUE_CAPACITY = 10;
    private static final BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<Order> inventoryQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final BlockingQueue<Order> paymentQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    private static final ExecutorService orderExecutor = Executors.newSingleThreadExecutor();
    private static final ExecutorService inventoryExecutor = Executors.newFixedThreadPool(2);
    private static final ExecutorService paymentExecutor = Executors.newFixedThreadPool(2);
    private static final ExecutorService confirmationExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        // Simulate adding orders to the order queue
        orderExecutor.execute(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    Order order = new Order(i);
                    orderQueue.put(order);
                    System.out.println("New order added: " + order.getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Process inventory
        inventoryExecutor.execute(() -> {
            while (true) {
                try {
                    Order order = orderQueue.take();
                    System.out.println("Processing inventory for order: " + order.getId());
                    inventoryQueue.put(order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Process payment
        paymentExecutor.execute(() -> {
            while (true) {
                try {
                    Order order = inventoryQueue.take();
                    System.out.println("Processing payment for order: " + order.getId());
                    paymentQueue.put(order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Confirm orders
        confirmationExecutor.execute(() -> {
            while (true) {
                try {
                    Order order = paymentQueue.take();
                    System.out.println("Order confirmed: " + order.getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Shutdown executors
        orderExecutor.shutdown();
        inventoryExecutor.shutdown();
        paymentExecutor.shutdown();
        confirmationExecutor.shutdown();

        orderExecutor.awaitTermination(1, TimeUnit.MINUTES);
        inventoryExecutor.awaitTermination(1, TimeUnit.MINUTES);
        paymentExecutor.awaitTermination(1, TimeUnit.MINUTES);
        confirmationExecutor.awaitTermination(1, TimeUnit.MINUTES);
    }
}


class Order {
    private final int id;

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}