package com.cool.java_core;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public class ComplexTask {
    private final int taskId;
    @Getter
    private int result;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        try {
            int delay = ThreadLocalRandom.current().nextInt(100, 500);
            System.out.println("Поток " + Thread.currentThread().getName() + " делает очень сложную задачу " + taskId + " стоимостью " + delay + " очков.");

            Thread.sleep(delay);

            result = delay;

            System.out.println("Работка с задачей " + taskId + " потоком " + Thread.currentThread().getName() + " окончена.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
