package org.example;

import javax.swing.*;
public class Main {
    private static void profiler() {
        try {
            Cpu cpu = new Cpu(16, true);
            Profiler profiler = new Profiler(cpu);
            System.out.println("\n With limit TLB limit 16: ");
            long delay = profiler.run(true);
            long EAT = delay/ProfilerStatistics.totalQueries;
            System.out.println("EAT with caching enabled: " + EAT + "ms");

            delay = profiler.run(false);
            EAT = delay/ProfilerStatistics.totalQueries;
            System.out.println("EAT without caching disabled: " + EAT+ "ms");

            cpu = new Cpu(32, true);
            System.out.println("\n With TLB limit 32: ");

            profiler = new Profiler(cpu);
            delay = profiler.run(true);
            EAT = delay/ProfilerStatistics.totalQueries;
            System.out.println("EAT with caching enabled: " + EAT + "ms");

            delay = profiler.run(false);
            EAT = delay/ProfilerStatistics.totalQueries;
            System.out.println("EAT without caching disabled: " + EAT + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void printSumWithTiming(Process p, String a, String b) throws Exception {
        long startTime = System.nanoTime();
        int ans = p.sum(a, b);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;
        System.out.println("The sum is: " + ans + ", Time taken: " + duration + "ms");
    }
    private static void run() {
        try {
            ProfilerStatistics.log(true);
            Cpu cpu = new Cpu(16, false);
            Process p = cpu.addProcess(9);
            p.assign("first_variable", 11);
            p.assign("second_variable", 22);
            printSumWithTiming(p,"first_variable", "second_variable");
            cpu.doCaching();
            printSumWithTiming(p,"first_variable", "second_variable");
            printSumWithTiming(p,"first_variable", "second_variable");

            Process p2 = cpu.addProcess(36);
            p2.assign("variable", 220);

            cpu.terminateProcess(p);
            Process p3 = cpu.addProcess(2);

            p3.assign("variable_1", 34345);
            p3.assign("variable_2", 1232);

            cpu.printRam();
        } catch (Exception e) {
           System.out.println(e.toString());
        }
    }
    public static void main(String[] args) {

        // Features
        // 1. Virtual memory management
        // 2. Page table and TLB implementation
        // 3. Kill process and reallocate memory
        // 4. Caching and invalidating the cache
        // 5. Display the RAM status in UI

        profiler();
//        run();
    }
}