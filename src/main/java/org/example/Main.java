package org.example;
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
            Cpu cpu = new Cpu(8, 16);
            Profiler profiler = new Profiler(cpu);
            System.out.println("With limit TLB limit 8: ");
            long delay = profiler.run(true);
            System.out.println("Time taken with caching enabled: " + delay + "ms");

            delay = profiler.run(false);
            System.out.println("Time taken without caching enabled: " + delay + "ms");

            cpu = new Cpu(16, 16);
            System.out.println("With TLB limit 16: ");

            profiler = new Profiler(cpu);
            delay = profiler.run(true);
            System.out.println("Time taken with caching enabled: " + delay + "ms");

            delay = profiler.run(false);
            System.out.println("Time taken without caching enabled: " + delay + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        try {
//            Cpu cpu = new Cpu(16, 16);
//            Process p = cpu.addProcess(18);
//            p.assign("a", 12);
//            p.assign("b", 13);
//            printSumWithTiming(p,"a", "b");
//            cpu.doCaching();
//            printSumWithTiming(p,"a", "b");
//            printSumWithTiming(p,"a", "b");
//
////            cpu.killProcess(p);
//
//            Process p3 = cpu.addProcess(2);
//            p3.assign("abcc", 34345);
//            p3.assign("abcc", 1232);
//            p3.assign("nnnn", 565);
//
//            cpu.printRam();
//        } catch (Exception e) {
////            System.out.println(e);
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