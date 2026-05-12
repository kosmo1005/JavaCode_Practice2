package com.cool.java_core.mainRunners;

import com.cool.java_core.BlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new BlockingQueue<>(5);

        ExecutorService producerPool = Executors.newFixedThreadPool(2);
        ExecutorService consumerPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int id = i;
            producerPool.submit(() -> {
                try {
                    String msg = "msg-" + id;
                    queue.enqueue(msg);
                    System.out.println("[PRODUCER] положил: " + msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            consumerPool.submit(() -> {
                try {
                    Thread.sleep(300); // искусственно замедляем потребителя

                    String msg = queue.dequeue();
                    System.out.println("[CONSUMER] достаю: " + msg);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        producerPool.shutdown();
        consumerPool.shutdown();

        producerPool.awaitTermination(5, TimeUnit.SECONDS);
        consumerPool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("DONE");
    }
}
