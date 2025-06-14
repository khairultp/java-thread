package com.khairul.stream;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PlatformThreadFailureDemo {

    public static void main(String[] args) {
        try {
            Instant start = Instant.now();

            try (var executor = Executors.newFixedThreadPool(100_000)) {
                IntStream.range(0, 100_000).forEach(i -> {
                    executor.submit(() -> {
                        try {
                            Thread.sleep(Duration.ofSeconds(0));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        if (i % 1000 == 0) {
                            System.out.println("Task " + i + " completed.");
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