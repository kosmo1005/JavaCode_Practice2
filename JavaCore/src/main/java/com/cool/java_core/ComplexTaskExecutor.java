package com.cool.java_core;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    private final List<Integer> results = new CopyOnWriteArrayList<>();
    private ExecutorService executor;
    private CyclicBarrier barrier;


    public void executeTasks(int numberOfTasks) {
         executor = Executors.newFixedThreadPool(numberOfTasks);

        barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("Все потоки достигли барьера. Собираем результат.");
            System.out.println("Итоговый результат: " + results);
            executor.shutdown();
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("done");
        });

        for (int i = 0; i < numberOfTasks; i++) {
            final int taskId = i;

            executor.submit(() -> {
                ComplexTask task = new ComplexTask(taskId);

                task.execute();
                results.add(task.getResult());

                try {
                    System.out.println(Thread.currentThread().getName()
                            + " ожидает у барьера");

                    barrier.await();

                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
