package com.khairul.thread;

import java.time.Duration;

public class PlatformThreadFailureDemo {

    public static void start(int threadNum) {
        System.out.printf("Starting simulation with %d tasks using Platform Threads....\n", threadNum);

        int checkPoint = (int) (threadNum * 0.01);

        try {
            for (int i = 1; i <= threadNum; i++) {
                Thread.ofPlatform().daemon(true).start(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

                if ((i+1) % checkPoint == 0) {
                    System.out.println("Task #-" + (i + 1) + " done.");
                }
            }
        } catch (Throwable t) {
            System.err.println("\n==============================================");
            System.err.println("Error: " + t.getClass().getName());
            System.err.println("Message: " + t.getMessage());
            System.err.println("==============================================");
        }
    }
}