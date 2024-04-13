package org.example;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {

        // Limitations
        // 1. supports only int variables
        // 2. the frames will be contiguous in memory

        // Features
        // 1. Virtual memory management
        // 2. Page table and TLB implementation
        // 3. Kill process and reallocate memory
        // 4. Caching and invalidating the cache

        try {
            Cpu cpu = new Cpu(16, false);
            Profiler profiler = new Profiler(cpu);
            System.out.println("\n With limit TLB limit 16: ");
            long delay = profiler.run(true);
            System.out.println("Time taken with caching enabled: " + delay + "ms");

            delay = profiler.run(false);
            System.out.println("Time taken without caching disabled: " + delay + "ms");

            cpu = new Cpu(32, false);
            System.out.println("\n With TLB limit 32: ");

            profiler = new Profiler(cpu);
            delay = profiler.run(true);
            System.out.println("Time taken with caching enabled: " + delay + "ms");

            delay = profiler.run(false);
            System.out.println("Time taken without caching disabled: " + delay + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        try {
//            ProfilerStatistics.log(true);
//            Cpu cpu = new Cpu();
//            Process p = cpu.addProcess(9);
//            p.assign("first_variable", 11);
//            p.assign("second_variable", 22);
//            printSumWithTiming(p,"first_variable", "second_variable");
//            cpu.doCaching();
//            printSumWithTiming(p,"first_variable", "second_variable");
//            printSumWithTiming(p,"first_variable", "second_variable");
//
//            cpu.terminateProcess(p);
//            cpu.addProcess(33);
//            Process p3 = cpu.addProcess(2);
//
//            p3.assign("variable_1", 34345);
//            p3.assign("variable_2", 1232);
//
//            cpu.printRam();
//        } catch (Exception e) {
//           System.out.println(e);
//        }

    }
    private static void printSumWithTiming(Process p, String a, String b) throws Exception {
        long startTime = System.nanoTime();
        int ans = p.sum(a, b);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;
        System.out.println("The sum is: " + ans + ", Time taken: " + duration + "ms");
    }
}