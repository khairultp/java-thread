package com.khairul.thread;

public class Main {
    public static void main(String[] args) {
        // Default thread count is 100.000, but can be overridden by command line argument
        int number = 100_000;

        if (args.length > 0) {
            try {
                number = Integer.parseInt(args[0]);
                System.out.println("Using thread count: " + number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid thread count argument. Using default: " + number);
            }
        } else {
            System.out.println("No thread count specified. Using default: " + number);
        }


        VirtualThreadSuccessDemo.start(number);
        System.out.println();

        PlatformThreadFailureDemo.start(number);
        System.out.println();

        FixedVirtualThreadPoolDemo.start(number);
        System.out.println();

        FixedPlatformThreadFailureDemo.start(number);
        System.out.println();
    }
}
