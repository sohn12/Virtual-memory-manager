package org.example;

public class Main {
    public static void main(String[] args) {

        // 1. supports only int variables
        // 2. a process will have fixed memory
        // 3. the frames will be contiguous in memory

        Cpu cpu = Cpu.getInstance();
        try {
            Process p = cpu.addProcess(24);
            p.assign("x", 22);
            p.assign("y", 23);

            printSumTiming(p, "x", "y");

            cpu.doCaching(); // cache the variables in cpu

            printSumTiming(p, "x", "y");
            printSumTiming(p, "x", "y");

//            Process p1 = cpu.addProcess(8);
//            p1.assign("abc" , 2200);
//            p1.assign("xyz", 33567);
//
//            System.out.println(p1.sum("abc", "xyz"));
            cpu.printRam();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static void printSumTiming(Process p, String a, String b) throws Exception {
        long startTime = System.nanoTime();
        int ans = p.sum(a, b);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;
        System.out.println("The sum is: " + ans + ", Time taken: " + duration + "ms");
    }
}