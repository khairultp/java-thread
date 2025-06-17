package com.khairul.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class FixedVirtualThreadPoolDemo {

    public static void start(int threadNum) {
        System.out.printf("Starting simulation with %d tasks using Fixed Virtual Thread Pool....\n", threadNum);

        int checkPoint = (int) (threadNum * 0.2);
        Instant start = Instant.now();

        // Create a custom ThreadFactory that produces virtual threads
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();

        // Create a fixed thread pool with virtual threads
        try (ExecutorService executor = new ThreadPoolExecutor(
                threadNum, // corePoolSize
                threadNum, // maximumPoolSize
                0L, TimeUnit.MILLISECONDS, // keepAliveTime
                new LinkedBlockingQueue<>(), // workQueue
                virtualThreadFactory)) {

            // Submit tasks to the executor
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

        System.out.println("==================================================");
        System.out.printf("All %d tasks done.\n", threadNum);
        System.out.printf("Total time: %d ms (%f seconds) \n", timeElapsed, (timeElapsed / 1000.0));
        System.out.println("==================================================");
    }
}