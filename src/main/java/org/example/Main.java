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


        // 1. Limit the TLB
        // 2. Increase the RAM size
        // 3. Add a class to randomly create processes and variables
        // 4. Performance reporting


//        Cpu cpu = Cpu.getInstance();
        Profiler profiler = new Profiler();

        try {
            profiler.run(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        try {
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