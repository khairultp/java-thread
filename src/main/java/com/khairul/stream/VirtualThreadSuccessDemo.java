package com.khairul.stream;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadSuccessDemo {

    public static void main(String[] args) {
        System.out.println("Starting simulation with 100,000 tasks using Virtual Threads....");
        Instant start = Instant.now();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if ((i+1) % 10_000 == 0) {
                        System.out.println("Task #-" + (i + 1) + " done.");
                    }
                });
            });
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("==================================================");
        System.out.println("All 100.000 task done.");
        System.out.println("Total time: " + timeElapsed + " ms (" + timeElapsed / 1000.0 + " seconds)");
        System.out.println("==================================================");
    }
}