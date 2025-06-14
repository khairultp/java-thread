package com.khairul.thread;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PlatformThreadMemoryFootprint {

    private static final List<Thread> threadList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            for (int i = 1; i <= 20_000; i++) {
                Thread platformThread = Thread.ofPlatform().daemon(true).start(() -> {
                    try {
                        Thread.sleep(Duration.ofMinutes(10));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                threadList.add(platformThread);

                if (i % 1000 == 0) {
                    printMemoryUsage(i);
                }
            }
        } catch (Throwable t) {
            System.err.println("\n==============================================");
            System.err.printf("ERROR on thread: [%d]\n", threadList.size());
            System.err.println("Error: " + t.getClass().getName());
            System.err.println("Message: " + t.getMessage());
            printMemoryUsage(threadList.size());
            System.err.println("==============================================");
        }
    }

    private static void printMemoryUsage(int threadCount) {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.printf("ERROR: %s \n", e.getMessage());
        }

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.printf("Threads: %,d -> Memori used: %,d MB%n",
                threadCount,
                usedMemory / (1024 * 1024));
    }
}