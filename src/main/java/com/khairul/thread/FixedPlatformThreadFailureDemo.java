package com.khairul.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FixedPlatformThreadFailureDemo {

    public static void start(int threadNum) {
        System.out.printf("Starting simulation with %d tasks using Fixed Platform Threads....\n", threadNum);

        int checkPoint = (int) (threadNum * 0.1);
        try {
            Instant start = Instant.now();

            try (var executor = Executors.newFixedThreadPool(threadNum)) {
                IntStream.range(0, threadNum).forEach(i -> {
                    executor.submit(() -> {
                        try {
                            Thread.sleep(Duration.ofSeconds(1));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        if ((i+1) % checkPoint == 0) {
                            System.out.println("Task #-" + (i + 1) + " done.");
                        }
                    });
                });
            }

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Total time: " + timeElapsed + " ms");

        } catch (Throwable t) {
            System.err.println("\n==============================================");
            System.err.println("Expected Error!");
            System.err.println("Error: " + t.getClass().getName());
            System.err.println("Message: " + t.getMessage());
            System.err.println("==============================================");
        }
    }
}